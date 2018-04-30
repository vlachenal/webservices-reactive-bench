/*
 * Copyright © 2017 Vincent Lachenal
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See the COPYING file for more details.
 */
package com.github.vlachenal.webservice.reactive.bench.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ParameterDisposer;
import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.jdbc.core.SqlProvider;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import reactor.core.publisher.Flux;


/**
 * JDBC template which manage flux for reactive service purpose
 *
 * @author Vincent Lachenal
 */
public class ReactiveJdbcTemplate extends JdbcTemplate {

  // Constructors +
  /**
   * {@link ReactiveJdbcTemplate} constructor
   *
   * @param dataSource the data source to use
   */
  public ReactiveJdbcTemplate(final DataSource dataSource) {
    super(dataSource);
  }
  // Constructors -


  // Methods +
  /**
   * Copy of JdbcTemplate
   * Determine SQL from potential provider object.
   *
   * @param sqlProvider object that's potentially a SqlProvider
   *
   * @return the SQL string, or {@code null}
   *
   * @see SqlProvider
   */
  @Nullable
  private static String getSql(final Object sqlProvider) {
    if(sqlProvider instanceof SqlProvider) {
      return ((SqlProvider)sqlProvider).getSql();
    } else {
      return null;
    }
  }

  /**
   * Reintegrate exectue method source code to release SQL connection and prepare statement.
   *
   * Query using a prepared statement, allowing for a PreparedStatementCreator
   * and a PreparedStatementSetter. Most other query methods use this method,
   * but application code will always work with either a creator or a setter.
   *
   * @param psc Callback handler that can create a PreparedStatement given a Connection
   * @param pss object that knows how to set values on the prepared statement.
   *            If this is null, the SQL will be assumed to contain no bind parameters.
   * @param rse object that will extract each row.
   *
   * @return an arbitrary result Flux, as returned by the RowMapper
   *
   * @throws DataAccessException if there is any problem
   */
  @Nullable
  public <T> Flux<T> queryForFlux(final PreparedStatementCreator psc, @Nullable final PreparedStatementSetter pss, final RowMapper<T> rowMapper) throws DataAccessException {
    Assert.notNull(rowMapper, "RowMapper must not be null");
    logger.debug("Executing prepared SQL query");
    Assert.notNull(psc, "PreparedStatementCreator must not be null");
    if(logger.isDebugEnabled()) {
      final String sql = getSql(psc);
      logger.debug("Executing prepared SQL statement" + (sql != null ? " [" + sql + "]" : ""));
    }

    final Connection con = DataSourceUtils.getConnection(obtainDataSource());
    PreparedStatement ps = null;
    ResultSet rs = null;
    try {
      final PreparedStatement ips = ps = psc.createPreparedStatement(con);
      applyStatementSettings(ps);
      Flux<T> result = null;
      if(pss != null) {
        pss.setValues(ps);
      }
      final ResultSet res = rs = ps.executeQuery(); // Duplicate variable for lambda usage
      final MutableInteger rowNum = new MutableInteger(0);
      result = Flux.<T>generate(emitter -> {
        try {
          if(res.next()) {
            emitter.next(rowMapper.mapRow(res, rowNum.addAndGet(1)));
          } else {
            emitter.complete();
          }
        } catch(final SQLException e) {
          emitter.error(translateException("Flux.get", getSql(psc), e));
        }
      }).doOnTerminate(() -> {
        JdbcUtils.closeResultSet(res);
        if(pss instanceof ParameterDisposer) {
          ((ParameterDisposer)pss).cleanupParameters();
        }
        JdbcUtils.closeStatement(ips);
        DataSourceUtils.releaseConnection(con, getDataSource());
      });
      handleWarnings(ps);
      return result;
    } catch(final SQLException ex) {
      // Release Connection early, to avoid potential connection pool deadlock
      // in the case when the exception translator hasn't been initialized yet.
      JdbcUtils.closeResultSet(rs);
      if(psc instanceof ParameterDisposer) {
        ((ParameterDisposer)psc).cleanupParameters();
      }
      final String sql = getSql(psc);
      JdbcUtils.closeStatement(ps);
      DataSourceUtils.releaseConnection(con, getDataSource());
      throw translateException("PreparedStatementCallback", sql, ex);
    }
  }

  /**
   * Query given SQL to create a prepared statement from SQL and a
   * list of arguments to bind to the query, expecting a result list.
   * <p>The results will be mapped to a List (one entry for each row) of
   * result objects, each of them matching the specified element type.
   *
   * @param sql SQL query to execute
   * @param args arguments to bind to the query
   * @param argTypes SQL types of the arguments (constants from {@code java.sql.Types})
   * @param elementType the required type of element in the result list (for example, {@code Integer.class})
   *
   * @return a Flux of objects that match the specified element type
   *
   * @throws DataAccessException if the query fails
   *
   * @see #queryForList(String, Class)
   * @see SingleColumnRowMapper
   */
  public <T> Flux<T> queryForFlux(final String sql, final Object[] args, final int[] argTypes, final Class<T> elementType) throws DataAccessException {
    return queryForFlux(new SimplePreparedStatementCreator(sql), newArgTypePreparedStatementSetter(args, argTypes), getSingleColumnRowMapper(elementType));
  }

  /**
   * Query given SQL to create a prepared statement from SQL and a
   * list of arguments to bind to the query, expecting a result list.
   * <p>The results will be mapped to a List (one entry for each row) of
   * result objects, each of them matching the specified element type.
   *
   * @param sql SQL query to execute
   * @param args arguments to bind to the query
   * @param elementType the required type of element in the result list (for example, {@code Integer.class})
   *
   * @return a Flux of objects that match the specified element type
   *
   * @throws DataAccessException if the query fails
   *
   * @see #queryForList(String, Class)
   * @see SingleColumnRowMapper
   */
  public <T> Flux<T> queryForFlux(final String sql, final Object[] args, final Class<T> elementType) throws DataAccessException {
    return queryForFlux(new SimplePreparedStatementCreator(sql), newArgPreparedStatementSetter(args), getSingleColumnRowMapper(elementType));
  }

  /**
   * Query given SQL to create a prepared statement from SQL and a
   * list of arguments to bind to the query, expecting a result list.
   * <p>The results will be mapped to a List (one entry for each row) of
   * result objects, each of them matching the specified element type.
   *
   * @param sql SQL query to execute
   * @param args arguments to bind to the query
   * @param elementType the required type of element in the result list (for example, {@code Integer.class})
   *
   * @return a Flux of objects that match the specified element type
   *
   * @throws DataAccessException if the query fails
   *
   * @see #queryForList(String, Class)
   * @see SingleColumnRowMapper
   */
  public <T> Flux<T> queryForFlux(final String sql, final Class<T> elementType, @Nullable final Object... args) throws DataAccessException {
    return queryForFlux(new SimplePreparedStatementCreator(sql), newArgPreparedStatementSetter(args), getSingleColumnRowMapper(elementType));
  }

  /**
   * Execute a query for a result list, given static SQL.
   * <p>Uses a JDBC Statement, not a PreparedStatement. If you want to
   * execute a static query with a PreparedStatement, use the overloaded
   * {@code queryForList} method with {@code null} as argument array.
   * <p>The results will be mapped to a List (one entry for each row) of
   * Maps (one entry for each column using the column name as the key).
   * Each element in the list will be of the form returned by this interface's
   * queryForMap() methods.
   *
   * @param sql SQL query to execute
   * @param args arguments to bind to the query
   * @param argTypes SQL types of the arguments (constants from {@code java.sql.Types})
   *
   * @return an Map that contains a Map per row
   *
   * @throws DataAccessException if there is any problem executing the query
   *
   * @see #queryForList(String, Object[])
   */
  public Flux<Map<String, Object>> queryForFlux(final String sql, final Object[] args, final int[] argTypes) throws DataAccessException {
    return queryForFlux(new SimplePreparedStatementCreator(sql), newArgTypePreparedStatementSetter(args, argTypes), getColumnMapRowMapper());
  }

  /**
   * Execute a query for a result list, given static SQL.
   * <p>Uses a JDBC Statement, not a PreparedStatement. If you want to
   * execute a static query with a PreparedStatement, use the overloaded
   * {@code queryForList} method with {@code null} as argument array.
   * <p>The results will be mapped to a List (one entry for each row) of
   * Maps (one entry for each column using the column name as the key).
   * Each element in the list will be of the form returned by this interface's
   * queryForMap() methods.
   *
   * @param sql SQL query to execute
   * @param args arguments to bind to the query
   *
   * @return an Map that contains a Map per row
   *
   * @throws DataAccessException if there is any problem executing the query
   *
   * @see #queryForList(String, Object[])
   */
  public Flux<Map<String, Object>> queryForFlux(final String sql, @Nullable final Object... args) throws DataAccessException {
    return queryForFlux(new SimplePreparedStatementCreator(sql), newArgPreparedStatementSetter(args), getColumnMapRowMapper());
  }

  /**
   * Query given SQL to create a prepared statement from SQL and a
   * list of arguments to bind to the query, expecting a result list.
   * <p>The results will be mapped to a List (one entry for each row) of
   * result objects, each of them matching the specified element type.
   *
   * @param sql SQL query to execute
   * @param elementType the required type of element in the result list (for example, {@code Integer.class})
   *
   * @return a Flux of objects that match the specified element type
   *
   * @throws DataAccessException if the query fails
   *
   * @see #queryForList(String, Class)
   * @see SingleColumnRowMapper
   */
  public <T> Flux<T> queryForFlux(final String sql, final Class<T> elementType) throws DataAccessException {
    return queryForFlux(new SimplePreparedStatementCreator(sql), null, getSingleColumnRowMapper(elementType));
  }

  /**
   * Execute a query for a result list, given static SQL.
   * <p>Uses a JDBC Statement, not a PreparedStatement. If you want to
   * execute a static query with a PreparedStatement, use the overloaded
   * {@code queryForList} method with {@code null} as argument array.
   * <p>The results will be mapped to a List (one entry for each row) of
   * Maps (one entry for each column using the column name as the key).
   * Each element in the list will be of the form returned by this interface's
   * queryForMap() methods.
   *
   * @param sql SQL query to execute
   *
   * @return an Map that contains a Map per row
   *
   * @throws DataAccessException if there is any problem executing the query
   *
   * @see #queryForList(String, Object[])
   */
  public Flux<Map<String, Object>> queryForFlux(final String sql) throws DataAccessException {
    return queryForFlux(new SimplePreparedStatementCreator(sql), null, getColumnMapRowMapper());
  }

  /**
   * Query given SQL to create a prepared statement from SQL and a list
   * of arguments to bind to the query, mapping each row to a Java object
   * via a RowMapper.
   *
   * @param sql SQL query to execute
   * @param rowMapper object that will map one object per row
   * @param args arguments to bind to the query
   *
   * @return the result Flux, containing mapped objects
   *
   * @throws DataAccessException if the query fails
   *
   * @see java.sql.Types
   */
  public <T> Flux<T> queryForFlux(final String sql, final RowMapper<T> rowMapper, @Nullable final Object... args) {
    return queryForFlux(new SimplePreparedStatementCreator(sql), newArgPreparedStatementSetter(args), rowMapper);
  }

  /**
   * Query given SQL to create a prepared statement from SQL and a list
   * of arguments to bind to the query, mapping each row to a Java object
   * via a RowMapper.
   *
   * @param sql SQL query to execute
   * @param args arguments to bind to the query
   * @param rowMapper object that will map one object per row
   *
   * @return the result Flux, containing mapped objects
   *
   * @throws DataAccessException if the query fails
   *
   * @see java.sql.Types
   */
  public <T> Flux<T> queryForFlux(final String sql, final Object[] args, final RowMapper<T> rowMapper) {
    return queryForFlux(new SimplePreparedStatementCreator(sql), newArgPreparedStatementSetter(args), rowMapper);
  }

  /**
   * Query given SQL to create a prepared statement from SQL and a list
   * of arguments to bind to the query, mapping each row to a Java object
   * via a RowMapper.
   *
   * @param sql SQL query to execute
   * @param args arguments to bind to the query
   * @param argTypes SQL types of the arguments (constants from {@code java.sql.Types})
   * @param rowMapper object that will map one object per row
   *
   * @return the result Flux, containing mapped objects
   *
   * @throws DataAccessException if the query fails
   *
   * @see java.sql.Types
   */
  public <T> Flux<T> queryForFlux(final String sql, final Object[] args, final int[] argTypes, final RowMapper<T> rowMapper) {
    return queryForFlux(new SimplePreparedStatementCreator(sql), newArgTypePreparedStatementSetter(args, argTypes), rowMapper);
  }

  /**
   * Execute multiple batches using the supplied SQL statement with the collect of supplied arguments.
   * The arguments' values will be set using the ParameterizedPreparedStatementSetter.
   * Each batch should be of size indicated in 'batchSize'.
   *
   * @param sql the SQL statement to execute.
   * @param batchArgs the List of Object arrays containing the batch of arguments for the query
   * @param batchSize batch size
   * @param pss ParameterizedPreparedStatementSetter to use
   *
   * @throws DataAccessException if there is any problem issuing the update
   */
  public <T> void batchUpdate(final String sql,
                              final Flux<T> batchArgs,
                              final int batchSize,
                              final ParameterizedPreparedStatementSetter<T> pss) throws DataAccessException {
    if(logger.isDebugEnabled()) {
      logger.debug("Executing SQL batch update [" + sql + "] with a batch size of " + batchSize);
    }
    final MutableInteger num = new MutableInteger(0);

    final PreparedStatementCreator psc = new SimplePreparedStatementCreator(sql);
    final Connection con = DataSourceUtils.getConnection(obtainDataSource());
    PreparedStatement ps = null;
    boolean batchSuppo = false;
    try {
      ps = psc.createPreparedStatement(con);
      applyStatementSettings(ps);
      batchSuppo = JdbcUtils.supportsBatchUpdates(ps.getConnection());
      if(!batchSuppo) {
        logger.warn("JDBC Driver does not support Batch updates; resorting to single statement execution");
      }
    } catch(final SQLException e) {
      if(psc instanceof ParameterDisposer) {
        ((ParameterDisposer)psc).cleanupParameters();
      }
      JdbcUtils.closeStatement(ps);
      DataSourceUtils.releaseConnection(con, getDataSource());
    }
    final boolean batchSupported = batchSuppo;
    final PreparedStatement stmt = ps;
    batchArgs.doOnNext(item -> {
      try {
        pss.setValues(stmt, item);
        final int n = num.addAndGet(1);
        if(batchSupported) {
          stmt.addBatch();
          if(n % batchSize == 0) {
            if(logger.isDebugEnabled()) {
              final int batchIdx = (n % batchSize == 0) ? n / batchSize : (n / batchSize) + 1;
              final int items = n - ((n % batchSize == 0) ? n / batchSize - 1 : (n / batchSize)) * batchSize;
              logger.debug("Sending SQL batch update #" + batchIdx + " with " + items + " items");
            }
            stmt.executeBatch();
          }
        } else {
          stmt.executeUpdate();
        }
      } catch(final SQLException e) {
        throw translateException("Flux.doOnNext", sql, e);
      }
    }).doOnComplete(() -> {
      if(batchSupported) {
        // Perform last update if not empty
        final int n = num.get();
        if(n % batchSize != 0) {
          if(logger.isDebugEnabled()) {
            final int batchIdx = (n % batchSize == 0) ? n / batchSize : (n / batchSize) + 1;
            final int items = n - ((n % batchSize == 0) ? n / batchSize - 1 : (n / batchSize)) * batchSize;
            logger.debug("Sending SQL batch update #" + batchIdx + " with " + items + " items");
          }
          try {
            stmt.executeBatch();
          } catch(final SQLException e) {
            throw translateException("Flux.doOnComplete", sql, e);
          }
        }
      }
    }).doFinally(t -> {
      if(psc instanceof ParameterDisposer) {
        ((ParameterDisposer)psc).cleanupParameters();
      }
      JdbcUtils.closeStatement(stmt);
      DataSourceUtils.releaseConnection(con, getDataSource());
    }).subscribe();
    try {
      handleWarnings(ps);
    } catch(final SQLException e) {
      if(psc instanceof ParameterDisposer) {
        ((ParameterDisposer)psc).cleanupParameters();
      }
      JdbcUtils.closeStatement(stmt);
      DataSourceUtils.releaseConnection(con, getDataSource());
    }
  }
  // Methods -


  // Classes +
  /**
   * Mutable integer ...
   *
   * @author Vincent Lachenal
   */
  public static class MutableInteger {

    /** Integer value underlying the object */
    private int val;

    /**
     * {@link MutableInteger} default constructor.<br>
     * Value is initialized to {@code 0}
     */
    public MutableInteger() {
      val = 0;
    }

    /**
     * {@link MutableInteger} constructor
     *
     * @param val the integer value
     */
    public MutableInteger(final int val) {
      this.val = val;
    }

    /**
     * Get value and increment
     *
     * @param incr the increment value
     *
     * @return the value
     */
    public int getAndAdd(final int incr) {
      final int val = this.val;
      this.val += incr;
      return val;
    }

    /**
     * Get value and increment
     *
     * @param incr the increment value
     *
     * @return the value
     */
    public int addAndGet(final int incr) {
      val += incr;
      return val;
    }

    /**
     * Get the integer value
     *
     * @return the value
     */
    public int get() {
      return val;
    }

  }

  /**
   * Copy of JdbcTemplte
   */
  private static class SimplePreparedStatementCreator implements PreparedStatementCreator, SqlProvider {

    private final String sql;

    public SimplePreparedStatementCreator(final String sql) {
      Assert.notNull(sql, "SQL must not be null");
      this.sql = sql;
    }

    @Override
    public PreparedStatement createPreparedStatement(final Connection con) throws SQLException {
      return con.prepareStatement(sql);
    }

    @Override
    public String getSql() {
      return sql;
    }
  }
  // Classes -

}

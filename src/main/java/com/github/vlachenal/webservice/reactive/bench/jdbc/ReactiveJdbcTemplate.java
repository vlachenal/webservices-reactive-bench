/*
 * Copyright © 2017 Vincent Lachenal
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See the COPYING file for more details.
 */
package com.github.vlachenal.webservice.reactive.bench.jdbc;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ParameterDisposer;
import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.RowMapper;
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
   * Query for stream
   *
   * @param <T> the stream type
   *
   * @param sql the SQL query
   * @param rowMapper the row mapper
   * @param args the prepared statement values
   *
   * @return the result stream
   */
  public <T> Flux<T> queryForFlux(final String sql, final RowMapper<T> rowMapper, @Nullable final Object... args) {
    return Flux.<T>create(emitter -> {
      final MutableInteger rowNum = new MutableInteger(1);
      query(sql, args, (rs) -> {
        emitter.next(rowMapper.mapRow(rs, rowNum.getAndAdd(1)));
      });
      emitter.complete();
    });
  }

  /**
   * SQL batch update
   *
   * @param sql the SQL query
   * @param batchArgs the batch flux
   * @param batchSize the batch size
   * @param pss the parameterized prepared statement
   *
   * @return plop
   *
   * @throws DataAccessException any error
   */
  public <T> int[][] batchUpdate(final String sql,
                                 final Flux<T> batchArgs,
                                 final int batchSize,
                                 final ParameterizedPreparedStatementSetter<T> pss) throws DataAccessException {

    if(logger.isDebugEnabled()) {
      logger.debug("Executing SQL batch update [" + sql + "] with a batch size of " + batchSize);
    }
    final List<int[]> rowsAffected = new ArrayList<>();
    final MutableInteger num = new MutableInteger(0);
    final int[][] result = execute(sql, (PreparedStatementCallback<int[][]>)ps -> {
      boolean plop = true;
      if(!JdbcUtils.supportsBatchUpdates(ps.getConnection())) {
        plop = false;
        logger.warn("JDBC Driver does not support Batch updates; resorting to single statement execution");
      }
      final boolean batchSupported = plop;
      try {
        batchArgs.doOnNext(item -> {
          try {
            pss.setValues(ps, item);
            final int n = num.addAndGet(1);
            if(batchSupported) {
              ps.addBatch();
              if(n % batchSize == 0) {
                if(logger.isDebugEnabled()) {
                  final int batchIdx = (n % batchSize == 0) ? n / batchSize : (n / batchSize) + 1;
                  final int items = n - ((n % batchSize == 0) ? n / batchSize - 1 : (n / batchSize)) * batchSize;
                  logger.debug("Sending SQL batch update #" + batchIdx + " with " + items + " items");
                }
                rowsAffected.add(ps.executeBatch());
              }
            } else {
              final int i = ps.executeUpdate();
              rowsAffected.add(new int[] { i });
            }
          } catch(final SQLException e) {
            System.err.println("Error " + e.getMessage());
            throw translateException("Flux.doOnNext", sql, e);
          }
        }).doOnComplete(() -> {
          // Perform last update if not empty
          final int n = num.get();
          if(n % batchSize != 0) {
            if(logger.isDebugEnabled()) {
              final int batchIdx = (n % batchSize == 0) ? n / batchSize : (n / batchSize) + 1;
              final int items = n - ((n % batchSize == 0) ? n / batchSize - 1 : (n / batchSize)) * batchSize;
              logger.debug("Sending SQL batch update #" + batchIdx + " with " + items + " items");
            }
            try {
              rowsAffected.add(ps.executeBatch());
            } catch(final SQLException e) {
              throw translateException("Flux.doOnComplete", sql, e);
            }
          }
        }).subscribe();
        final int[][] result1 = new int[rowsAffected.size()][];
        for(int i = 0 ; i < result1.length ; ++i) {
          result1[i] = rowsAffected.get(i);
        }
        return result1;
      } finally {
        // Cleanup parameters
        if(pss instanceof ParameterDisposer) {
          ((ParameterDisposer)pss).cleanupParameters();
        }
      }
    });

    Assert.state(result != null, "No result array");
    return result;
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
     * Value is initialized to <code>1</code>
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
  // Classes -

}

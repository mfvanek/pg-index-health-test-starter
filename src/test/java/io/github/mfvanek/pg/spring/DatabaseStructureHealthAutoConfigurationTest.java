/*
 * Copyright (c) 2021-2021. Ivan Vakhrushev.
 * https://github.com/mfvanek/pg-index-health-test-starter
 *
 * This file is a part of "pg-index-health-test-starter".
 *
 * Licensed under the Apache License 2.0
 */

package io.github.mfvanek.pg.spring;

import io.github.mfvanek.pg.connection.PgConnection;
import io.github.mfvanek.pg.index.maintenance.IndexesMaintenanceOnHost;
import io.github.mfvanek.pg.table.maintenance.TablesMaintenanceOnHost;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import org.springframework.boot.test.context.FilteredClassLoader;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.support.GenericApplicationContext;

import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;

class DatabaseStructureHealthAutoConfigurationTest {

  private final ApplicationContextRunner contextRunner = new ApplicationContextRunner();

  @Test
  void withoutDataSource() {
    this.contextRunner.withUserConfiguration(DatabaseStructureHealthAutoConfiguration.class)
        .run(context -> {
          assertThat(context).doesNotHaveBean("pgConnection");
          assertThat(context).doesNotHaveBean("indexesMaintenance");
          assertThat(context).doesNotHaveBean("tablesMaintenance");
        });
  }

  @Test
  void withDataSource() {
    this.contextRunner.withUserConfiguration(DatabaseStructureHealthAutoConfiguration.class)
        .withInitializer(ctx -> {
          GenericApplicationContext context = (GenericApplicationContext) ctx;
          context.registerBean("dataSource", DataSource.class, () -> Mockito.mock(DataSource.class));
        })
        .run(context -> {
          assertThat(context).hasBean("pgConnection");
          assertThat(context).hasBean("indexesMaintenance");
          assertThat(context).hasBean("tablesMaintenance");
        });
  }

  @ParameterizedTest
  @ValueSource(classes = {PgConnection.class, IndexesMaintenanceOnHost.class, TablesMaintenanceOnHost.class})
  void withoutClass(Class<?> type) {
    this.contextRunner.withUserConfiguration(DatabaseStructureHealthAutoConfiguration.class)
        .withInitializer(ctx -> {
          GenericApplicationContext context = (GenericApplicationContext) ctx;
          context.registerBean("dataSource", DataSource.class, () -> Mockito.mock(DataSource.class));
        })
        .withClassLoader(new FilteredClassLoader(type))
        .run(context -> {
          assertThat(context).doesNotHaveBean("pgConnection");
          assertThat(context).doesNotHaveBean("indexesMaintenance");
          assertThat(context).doesNotHaveBean("tablesMaintenance");
        });
  }
}
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
import io.github.mfvanek.pg.settings.maintenance.ConfigurationMaintenanceOnHost;
import io.github.mfvanek.pg.statistics.maintenance.StatisticsMaintenanceOnHost;
import io.github.mfvanek.pg.table.maintenance.TablesMaintenanceOnHost;
import org.apache.commons.text.WordUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import org.springframework.boot.test.context.FilteredClassLoader;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

import javax.annotation.Nonnull;
import javax.sql.DataSource;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class DatabaseStructureHealthAutoConfigurationTest {

    private static final Set<String> ALL_BEAN_NAMES = Collections.unmodifiableSet(
            Stream.of("pgConnection", "indexesMaintenance", "tablesMaintenance", "statisticsMaintenance", "configurationMaintenance")
                    .collect(Collectors.toSet()));
    private static final Set<String> MAIN_BEAN_NAMES = Collections.unmodifiableSet(
            Stream.of("indexesMaintenance", "tablesMaintenance", "statisticsMaintenance", "configurationMaintenance")
                    .collect(Collectors.toSet()));

    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner();

    @Test
    void withoutDataSource() {
        withTestConfig()
                .run(context ->
                        ALL_BEAN_NAMES.forEach(beanName ->
                                assertThat(context).doesNotHaveBean(beanName)));
    }

    @Test
    void withDataSource() {
        withTestConfig()
                .withInitializer(DatabaseStructureHealthAutoConfigurationTest::initialize)
                .run(context ->
                        ALL_BEAN_NAMES.forEach(beanName ->
                                assertThat(context).hasBean(beanName)));
    }

    @Test
    void withoutPgConnectionClass() {
        AccessController.doPrivileged((PrivilegedAction<?>) () -> {
            withTestConfig()
                    .withInitializer(DatabaseStructureHealthAutoConfigurationTest::initialize)
                    .withClassLoader(new FilteredClassLoader(PgConnection.class))
                    .run(context ->
                            ALL_BEAN_NAMES.forEach(beanName ->
                                    assertThat(context).doesNotHaveBean(beanName)));
            return null;
        });
    }

    @ParameterizedTest
    @ValueSource(classes = {
            IndexesMaintenanceOnHost.class,
            TablesMaintenanceOnHost.class,
            StatisticsMaintenanceOnHost.class,
            ConfigurationMaintenanceOnHost.class})
    void withoutClass(Class<?> type) {
        AccessController.doPrivileged((PrivilegedAction<?>) () -> {
            withTestConfig()
                    .withInitializer(DatabaseStructureHealthAutoConfigurationTest::initialize)
                    .withClassLoader(new FilteredClassLoader(type))
                    .run(context -> {
                        assertThat(context).hasBean("pgConnection");
                        MAIN_BEAN_NAMES.forEach(beanName -> {
                            final String beanNameByType = getBeanName(type);
                            if (beanName.equals(beanNameByType)) {
                                assertThat(context).doesNotHaveBean(beanName);
                            } else {
                                assertThat(context).hasBean(beanName);
                            }
                        });
                    });
            return null;
        });
    }

    @Nonnull
    private ApplicationContextRunner withTestConfig() {
        return contextRunner.withUserConfiguration(DatabaseStructureHealthAutoConfiguration.class);
    }

    private static <C extends ConfigurableApplicationContext> void initialize(@Nonnull C applicationContext) {
        GenericApplicationContext context = (GenericApplicationContext) applicationContext;
        context.registerBean("dataSource", DataSource.class, () -> Mockito.mock(DataSource.class));
    }

    @Nonnull
    private static String getBeanName(@Nonnull Class<?> type) {
        final String className = WordUtils.uncapitalize(type.getSimpleName());
        return className.substring(0, className.length() - "OnHost".length());
    }
}

/*
 * Copyright (c) 2021-2022. Ivan Vakhrushev.
 * https://github.com/mfvanek/pg-index-health-test-starter
 *
 * This file is a part of "pg-index-health-test-starter".
 *
 * Licensed under the Apache License 2.0
 */

package io.github.mfvanek.pg.spring;

import io.github.mfvanek.pg.checks.host.DuplicatedIndexesCheckOnHost;
import io.github.mfvanek.pg.checks.host.ForeignKeysNotCoveredWithIndexCheckOnHost;
import io.github.mfvanek.pg.checks.host.IndexesWithBloatCheckOnHost;
import io.github.mfvanek.pg.checks.host.IndexesWithNullValuesCheckOnHost;
import io.github.mfvanek.pg.checks.host.IntersectedIndexesCheckOnHost;
import io.github.mfvanek.pg.checks.host.InvalidIndexesCheckOnHost;
import io.github.mfvanek.pg.checks.host.TablesWithBloatCheckOnHost;
import io.github.mfvanek.pg.checks.host.TablesWithMissingIndexesCheckOnHost;
import io.github.mfvanek.pg.checks.host.TablesWithoutPrimaryKeyCheckOnHost;
import io.github.mfvanek.pg.checks.host.UnusedIndexesCheckOnHost;
import io.github.mfvanek.pg.connection.PgConnection;
import io.github.mfvanek.pg.settings.maintenance.ConfigurationMaintenanceOnHost;
import io.github.mfvanek.pg.statistics.maintenance.StatisticsMaintenanceOnHost;
import org.apache.commons.text.WordUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import org.springframework.boot.test.context.FilteredClassLoader;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nonnull;
import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;

class DatabaseStructureHealthAutoConfigurationTest {

    private static final Set<String> ALL_BEAN_NAMES = Collections.unmodifiableSet(
        Stream.of("pgConnection", "duplicatedIndexesCheck", "foreignKeysNotCoveredWithIndexCheck", "indexesWithBloatCheck",
                "indexesWithNullValuesCheck", "intersectedIndexesCheck", "invalidIndexesCheck", "tablesWithBloatCheck",
                "tablesWithMissingIndexesCheck", "tablesWithoutPrimaryKeyCheck", "unusedIndexesCheck",
                "statisticsMaintenance", "configurationMaintenance")
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
    void shouldNotCreateAutoConfigurationWithDisabledProperty() {
        withTestConfig()
            .withPropertyValues("pg.index.health.test.enabled=false")
            .withInitializer(DatabaseStructureHealthAutoConfigurationTest::initialize)
            .run(context ->
                ALL_BEAN_NAMES.forEach(beanName ->
                    assertThat(context).doesNotHaveBean(beanName)));
    }

    @Test
    void shouldCreateAutoConfigurationWhenPropertyExplicitlySet() {
        withTestConfig()
            .withPropertyValues("pg.index.health.test.enabled=true")
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
        DuplicatedIndexesCheckOnHost.class,
        ForeignKeysNotCoveredWithIndexCheckOnHost.class,
        IndexesWithBloatCheckOnHost.class,
        IndexesWithNullValuesCheckOnHost.class,
        IntersectedIndexesCheckOnHost.class,
        InvalidIndexesCheckOnHost.class,
        TablesWithBloatCheckOnHost.class,
        TablesWithMissingIndexesCheckOnHost.class,
        TablesWithoutPrimaryKeyCheckOnHost.class,
        UnusedIndexesCheckOnHost.class,
        StatisticsMaintenanceOnHost.class,
        ConfigurationMaintenanceOnHost.class})
    void withoutClass(final Class<?> type) {
        AccessController.doPrivileged((PrivilegedAction<?>) () -> {
            withTestConfig()
                .withInitializer(DatabaseStructureHealthAutoConfigurationTest::initialize)
                .withClassLoader(new FilteredClassLoader(type))
                .run(context -> {
                    assertThat(context).hasBean("pgConnection");
                    ALL_BEAN_NAMES.stream()
                        .filter(n -> !"pgConnection".equals(n))
                        .forEach(beanName -> {
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

    private static <C extends ConfigurableApplicationContext> void initialize(@Nonnull final C applicationContext) {
        final GenericApplicationContext context = (GenericApplicationContext) applicationContext;
        context.registerBean("dataSource", DataSource.class, () -> Mockito.mock(DataSource.class));
    }

    @Nonnull
    private static String getBeanName(@Nonnull final Class<?> type) {
        final String className = WordUtils.uncapitalize(type.getSimpleName());
        return className.substring(0, className.length() - "OnHost".length());
    }
}

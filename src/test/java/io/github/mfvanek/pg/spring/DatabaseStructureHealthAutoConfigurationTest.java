/*
 * Copyright (c) 2021-2022. Ivan Vakhrushev.
 * https://github.com/mfvanek/pg-index-health-test-starter
 *
 * This file is a part of "pg-index-health-test-starter".
 *
 * Licensed under the Apache License 2.0
 */

package io.github.mfvanek.pg.spring;

import io.github.mfvanek.pg.checks.host.ColumnsWithoutDescriptionCheckOnHost;
import io.github.mfvanek.pg.checks.host.DuplicatedIndexesCheckOnHost;
import io.github.mfvanek.pg.checks.host.ForeignKeysNotCoveredWithIndexCheckOnHost;
import io.github.mfvanek.pg.checks.host.IndexesWithBloatCheckOnHost;
import io.github.mfvanek.pg.checks.host.IndexesWithNullValuesCheckOnHost;
import io.github.mfvanek.pg.checks.host.IntersectedIndexesCheckOnHost;
import io.github.mfvanek.pg.checks.host.InvalidIndexesCheckOnHost;
import io.github.mfvanek.pg.checks.host.TablesWithBloatCheckOnHost;
import io.github.mfvanek.pg.checks.host.TablesWithMissingIndexesCheckOnHost;
import io.github.mfvanek.pg.checks.host.TablesWithoutDescriptionCheckOnHost;
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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import javax.annotation.Nonnull;
import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

class DatabaseStructureHealthAutoConfigurationTest {

    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner();

    private final Predicate<String> beanNamesFilter = b -> !b.startsWith("org.springframework") && !b.startsWith("pg.index.health.test") &&
        !b.endsWith("AutoConfiguration") && !"dataSource".equals(b);

    private static final List<String> EXPECTED_BEANS = Collections.unmodifiableList(Arrays.asList(
        "pgConnection",
        "duplicatedIndexesCheckOnHost",
        "foreignKeysNotCoveredWithIndexCheckOnHost",
        "indexesWithBloatCheckOnHost",
        "indexesWithNullValuesCheckOnHost",
        "intersectedIndexesCheckOnHost",
        "invalidIndexesCheckOnHost",
        "tablesWithBloatCheckOnHost",
        "tablesWithMissingIndexesCheckOnHost",
        "tablesWithoutPrimaryKeyCheckOnHost",
        "unusedIndexesCheckOnHost",
        "tablesWithoutDescriptionCheckOnHost",
        "columnsWithoutDescriptionCheckOnHost",
        "statisticsMaintenanceOnHost",
        "configurationMaintenanceOnHost"
    ));

    @Test
    void withoutDataSource() {
        assertWithTestConfig()
            .run(context -> assertThat(context.getBeanDefinitionNames())
                .isNotEmpty()
                .filteredOn(beanNamesFilter)
                .isEmpty());
    }

    @Test
    void withDataSource() {
        assertWithTestConfig()
            .withInitializer(DatabaseStructureHealthAutoConfigurationTest::initialize)
            .run(context -> {
                assertThat(context.getBeanDefinitionNames())
                    .filteredOn(beanNamesFilter)
                    .hasSize(15)
                    .containsAll(EXPECTED_BEANS);
                EXPECTED_BEANS.forEach(n ->
                    assertThat(context.getBean(n))
                        .isNotNull()
                );
            });
    }

    @Test
    void shouldNotCreateAutoConfigurationWithDisabledProperty() {
        assertWithTestConfig()
            .withPropertyValues("pg.index.health.test.enabled=false")
            .withInitializer(DatabaseStructureHealthAutoConfigurationTest::initialize)
            .run(context -> assertThat(context.getBeanDefinitionNames())
                .isNotEmpty()
                .filteredOn(beanNamesFilter)
                .isEmpty());
    }

    @Test
    void shouldCreateAutoConfigurationWhenPropertyExplicitlySet() {
        assertWithTestConfig()
            .withPropertyValues("pg.index.health.test.enabled=true")
            .withInitializer(DatabaseStructureHealthAutoConfigurationTest::initialize)
            .run(context -> assertThat(context.getBeanDefinitionNames())
                .isNotEmpty()
                .filteredOn(beanNamesFilter)
                .hasSize(15));
    }

    @Test
    void withoutPgConnectionClass() {
        assertThatCode(() -> AccessController.doPrivileged((PrivilegedAction<?>) () -> {
            assertWithTestConfig()
                .withInitializer(DatabaseStructureHealthAutoConfigurationTest::initialize)
                .withClassLoader(new FilteredClassLoader(PgConnection.class))
                .run(context -> assertThat(context.getBeanDefinitionNames())
                    .isNotEmpty()
                    .filteredOn(beanNamesFilter)
                    .isEmpty());
            return null;
        })).doesNotThrowAnyException();
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
        TablesWithoutDescriptionCheckOnHost.class,
        ColumnsWithoutDescriptionCheckOnHost.class,
        StatisticsMaintenanceOnHost.class,
        ConfigurationMaintenanceOnHost.class})
    void withoutClass(final Class<?> type) {
        AccessController.doPrivileged((PrivilegedAction<?>) () -> {
            assertWithTestConfig()
                .withInitializer(DatabaseStructureHealthAutoConfigurationTest::initialize)
                .withClassLoader(new FilteredClassLoader(type))
                .run(context -> assertThat(context)
                    .hasBean("pgConnection")
                    .doesNotHaveBean(getBeanName(type))
                    .satisfies(c -> assertThat(c.getBeanDefinitionNames())
                        .isNotEmpty()
                        .filteredOn(beanNamesFilter)
                        .hasSize(14)
                        .allSatisfy(n -> assertThat(context.getBean(n))
                            .isNotNull())));
            return null;
        });
    }

    @Nonnull
    private ApplicationContextRunner assertWithTestConfig() {
        return contextRunner.withUserConfiguration(DatabaseStructureHealthAutoConfiguration.class);
    }

    private static <C extends ConfigurableApplicationContext> void initialize(@Nonnull final C applicationContext) {
        final GenericApplicationContext context = (GenericApplicationContext) applicationContext;
        context.registerBean("dataSource", DataSource.class, () -> Mockito.mock(DataSource.class));
    }

    @Nonnull
    private static String getBeanName(@Nonnull final Class<?> type) {
        return WordUtils.uncapitalize(type.getSimpleName());
    }
}

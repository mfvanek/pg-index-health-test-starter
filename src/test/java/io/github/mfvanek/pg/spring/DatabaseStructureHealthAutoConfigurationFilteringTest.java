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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.context.FilteredClassLoader;

import java.security.AccessController;
import java.security.PrivilegedAction;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

class DatabaseStructureHealthAutoConfigurationFilteringTest extends AutoConfigurationTestBase {

    @Test
    void withoutPgConnectionClass() {
        assertThatCode(() -> AccessController.doPrivileged((PrivilegedAction<?>) () -> {
            assertWithTestConfig()
                .withInitializer(AutoConfigurationTestBase::initialize)
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
                .withInitializer(AutoConfigurationTestBase::initialize)
                .withClassLoader(new FilteredClassLoader(type))
                .run(context -> assertThat(context)
                    .hasBean("pgConnection")
                    .doesNotHaveBean(getBeanName(type))
                    .satisfies(c -> assertThat(c.getBeanDefinitionNames())
                        .isNotEmpty()
                        .filteredOn(beanNamesFilter)
                        .hasSize(EXPECTED_BEANS.size() - 1)
                        .allSatisfy(n -> assertThat(context.getBean(n))
                            .isInstanceOfAny(EXPECTED_TYPES))));
            return null;
        });
    }
}

/*
 * Copyright (c) 2021-2022. Ivan Vakhrushev.
 * https://github.com/mfvanek/pg-index-health-test-starter
 *
 * This file is a part of "pg-index-health-test-starter".
 *
 * Licensed under the Apache License 2.0
 */

package io.github.mfvanek.pg.spring;

import io.github.mfvanek.pg.common.maintenance.DatabaseCheckOnHost;
import io.github.mfvanek.pg.connection.PgConnection;
import io.github.mfvanek.pg.settings.maintenance.ConfigurationMaintenanceOnHost;
import io.github.mfvanek.pg.statistics.maintenance.StatisticsMaintenanceOnHost;
import org.apache.commons.text.WordUtils;
import org.mockito.Mockito;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

import java.util.List;
import java.util.function.Predicate;
import javax.annotation.Nonnull;
import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;

abstract class AutoConfigurationTestBase {

    protected static final List<String> EXPECTED_BEANS = List.of(
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
        "columnsWithJsonTypeCheckOnHost",
        "columnsWithSerialTypesCheckOnHost",
        "functionsWithoutDescriptionCheckOnHost",
        "statisticsMaintenanceOnHost",
        "configurationMaintenanceOnHost"
    );
    protected static final Class<?>[] EXPECTED_TYPES = {PgConnection.class, DatabaseCheckOnHost.class, StatisticsMaintenanceOnHost.class, ConfigurationMaintenanceOnHost.class};

    protected final ApplicationContextRunner contextRunner = new ApplicationContextRunner();
    protected final Predicate<String> beanNamesFilter = b -> !b.startsWith("org.springframework") && !b.startsWith("pg.index.health.test") &&
        !b.endsWith("AutoConfiguration") && !"dataSource".equals(b);

    @Nonnull
    protected ApplicationContextRunner assertWithTestConfig() {
        return contextRunner.withUserConfiguration(DatabaseStructureHealthAutoConfiguration.class);
    }

    protected static <C extends ConfigurableApplicationContext> void initialize(@Nonnull final C applicationContext) {
        final GenericApplicationContext context = (GenericApplicationContext) applicationContext;
        context.registerBean("dataSource", DataSource.class, () -> Mockito.mock(DataSource.class));
    }

    @Nonnull
    protected static String getBeanName(@Nonnull final Class<?> type) {
        return WordUtils.uncapitalize(type.getSimpleName());
    }

    protected void assertThatBeansAreNotNullBean(@Nonnull final ConfigurableApplicationContext context) {
        EXPECTED_BEANS.forEach(beanName ->
            assertThatBeanIsNotNullBean(context, beanName));
    }

    protected void assertThatBeanIsNotNullBean(@Nonnull final ConfigurableApplicationContext context, @Nonnull final String beanName) {
        assertThat(context.getBean(beanName))
            .isInstanceOfAny(EXPECTED_TYPES);
    }
}

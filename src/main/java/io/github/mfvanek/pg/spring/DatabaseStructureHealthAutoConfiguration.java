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
import io.github.mfvanek.pg.connection.PgConnectionImpl;
import io.github.mfvanek.pg.settings.maintenance.ConfigurationMaintenanceOnHost;
import io.github.mfvanek.pg.settings.maintenance.ConfigurationMaintenanceOnHostImpl;
import io.github.mfvanek.pg.statistics.maintenance.StatisticsMaintenanceOnHost;
import io.github.mfvanek.pg.statistics.maintenance.StatisticsMaintenanceOnHostImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * Autoconfiguration for using pg-index-health in unit tests.
 *
 * @author Ivan Vakhrushev
 * @since 2021.05.22
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(DatabaseStructureHealthProperties.class)
@ConditionalOnClass(DataSource.class)
@ConditionalOnProperty(name = "pg.index.health.test.enabled", matchIfMissing = true, havingValue = "true")
@AutoConfigureAfter({DataSourceAutoConfiguration.class})
public class DatabaseStructureHealthAutoConfiguration {

    @Bean
    @ConditionalOnClass(PgConnection.class)
    @ConditionalOnBean(name = "dataSource")
    @ConditionalOnMissingBean
    public PgConnection pgConnection(@Qualifier("dataSource") final DataSource dataSource) {
        return PgConnectionImpl.ofPrimary(dataSource);
    }

    @Bean
    @ConditionalOnClass(DuplicatedIndexesCheckOnHost.class)
    @ConditionalOnBean(PgConnection.class)
    @ConditionalOnMissingBean
    public DuplicatedIndexesCheckOnHost duplicatedIndexesCheck(final PgConnection pgConnection) {
        return new DuplicatedIndexesCheckOnHost(pgConnection);
    }

    @Bean
    @ConditionalOnClass(ForeignKeysNotCoveredWithIndexCheckOnHost.class)
    @ConditionalOnBean(PgConnection.class)
    @ConditionalOnMissingBean
    public ForeignKeysNotCoveredWithIndexCheckOnHost foreignKeysNotCoveredWithIndexCheck(final PgConnection pgConnection) {
        return new ForeignKeysNotCoveredWithIndexCheckOnHost(pgConnection);
    }

    @Bean
    @ConditionalOnClass(IndexesWithBloatCheckOnHost.class)
    @ConditionalOnBean(PgConnection.class)
    @ConditionalOnMissingBean
    public IndexesWithBloatCheckOnHost indexesWithBloatCheck(final PgConnection pgConnection) {
        return new IndexesWithBloatCheckOnHost(pgConnection);
    }

    @Bean
    @ConditionalOnClass(IndexesWithNullValuesCheckOnHost.class)
    @ConditionalOnBean(PgConnection.class)
    @ConditionalOnMissingBean
    public IndexesWithNullValuesCheckOnHost indexesWithNullValuesCheck(final PgConnection pgConnection) {
        return new IndexesWithNullValuesCheckOnHost(pgConnection);
    }

    @Bean
    @ConditionalOnClass(IntersectedIndexesCheckOnHost.class)
    @ConditionalOnBean(PgConnection.class)
    @ConditionalOnMissingBean
    public IntersectedIndexesCheckOnHost intersectedIndexesCheck(final PgConnection pgConnection) {
        return new IntersectedIndexesCheckOnHost(pgConnection);
    }

    @Bean
    @ConditionalOnClass(InvalidIndexesCheckOnHost.class)
    @ConditionalOnBean(PgConnection.class)
    @ConditionalOnMissingBean
    public InvalidIndexesCheckOnHost invalidIndexesCheck(final PgConnection pgConnection) {
        return new InvalidIndexesCheckOnHost(pgConnection);
    }

    @Bean
    @ConditionalOnClass(TablesWithBloatCheckOnHost.class)
    @ConditionalOnBean(PgConnection.class)
    @ConditionalOnMissingBean
    public TablesWithBloatCheckOnHost tablesWithBloatCheck(final PgConnection pgConnection) {
        return new TablesWithBloatCheckOnHost(pgConnection);
    }

    @Bean
    @ConditionalOnClass(TablesWithMissingIndexesCheckOnHost.class)
    @ConditionalOnBean(PgConnection.class)
    @ConditionalOnMissingBean
    public TablesWithMissingIndexesCheckOnHost tablesWithMissingIndexesCheck(final PgConnection pgConnection) {
        return new TablesWithMissingIndexesCheckOnHost(pgConnection);
    }

    @Bean
    @ConditionalOnClass(TablesWithoutPrimaryKeyCheckOnHost.class)
    @ConditionalOnBean(PgConnection.class)
    @ConditionalOnMissingBean
    public TablesWithoutPrimaryKeyCheckOnHost tablesWithoutPrimaryKeyCheck(final PgConnection pgConnection) {
        return new TablesWithoutPrimaryKeyCheckOnHost(pgConnection);
    }

    @Bean
    @ConditionalOnClass(UnusedIndexesCheckOnHost.class)
    @ConditionalOnBean(PgConnection.class)
    @ConditionalOnMissingBean
    public UnusedIndexesCheckOnHost unusedIndexesCheck(final PgConnection pgConnection) {
        return new UnusedIndexesCheckOnHost(pgConnection);
    }

    @Bean
    @ConditionalOnClass(StatisticsMaintenanceOnHost.class)
    @ConditionalOnBean(PgConnection.class)
    @ConditionalOnMissingBean
    public StatisticsMaintenanceOnHost statisticsMaintenance(final PgConnection pgConnection) {
        return new StatisticsMaintenanceOnHostImpl(pgConnection);
    }

    @Bean
    @ConditionalOnClass(ConfigurationMaintenanceOnHost.class)
    @ConditionalOnBean(PgConnection.class)
    @ConditionalOnMissingBean
    public ConfigurationMaintenanceOnHost configurationMaintenance(final PgConnection pgConnection) {
        return new ConfigurationMaintenanceOnHostImpl(pgConnection);
    }
}

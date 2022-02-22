/*
 * Copyright (c) 2021-2022. Ivan Vakhrushev.
 * https://github.com/mfvanek/pg-index-health-test-starter
 *
 * This file is a part of "pg-index-health-test-starter".
 *
 * Licensed under the Apache License 2.0
 */

package io.github.mfvanek.pg.spring;

import io.github.mfvanek.pg.connection.PgConnection;
import io.github.mfvanek.pg.connection.PgConnectionImpl;
import io.github.mfvanek.pg.index.maintenance.IndexMaintenanceOnHostImpl;
import io.github.mfvanek.pg.index.maintenance.IndexesMaintenanceOnHost;
import io.github.mfvanek.pg.settings.maintenance.ConfigurationMaintenanceOnHost;
import io.github.mfvanek.pg.settings.maintenance.ConfigurationMaintenanceOnHostImpl;
import io.github.mfvanek.pg.statistics.maintenance.StatisticsMaintenanceOnHost;
import io.github.mfvanek.pg.statistics.maintenance.StatisticsMaintenanceOnHostImpl;
import io.github.mfvanek.pg.table.maintenance.TablesMaintenanceOnHost;
import io.github.mfvanek.pg.table.maintenance.TablesMaintenanceOnHostImpl;
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
    public PgConnection pgConnection(@Qualifier("dataSource") DataSource dataSource) {
        return PgConnectionImpl.ofPrimary(dataSource);
    }

    @Bean
    @ConditionalOnClass(IndexesMaintenanceOnHost.class)
    @ConditionalOnBean(PgConnection.class)
    @ConditionalOnMissingBean
    public IndexesMaintenanceOnHost indexesMaintenance(PgConnection pgConnection) {
        return new IndexMaintenanceOnHostImpl(pgConnection);
    }

    @Bean
    @ConditionalOnClass(TablesMaintenanceOnHost.class)
    @ConditionalOnBean(PgConnection.class)
    @ConditionalOnMissingBean
    public TablesMaintenanceOnHost tablesMaintenance(PgConnection pgConnection) {
        return new TablesMaintenanceOnHostImpl(pgConnection);
    }

    @Bean
    @ConditionalOnClass(StatisticsMaintenanceOnHost.class)
    @ConditionalOnBean(PgConnection.class)
    @ConditionalOnMissingBean
    public StatisticsMaintenanceOnHost statisticsMaintenance(PgConnection pgConnection) {
        return new StatisticsMaintenanceOnHostImpl(pgConnection);
    }

    @Bean
    @ConditionalOnClass(ConfigurationMaintenanceOnHost.class)
    @ConditionalOnBean(PgConnection.class)
    @ConditionalOnMissingBean
    public ConfigurationMaintenanceOnHost configurationMaintenance(PgConnection pgConnection) {
        return new ConfigurationMaintenanceOnHostImpl(pgConnection);
    }
}

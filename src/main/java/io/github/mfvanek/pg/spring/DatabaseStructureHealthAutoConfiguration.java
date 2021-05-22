package io.github.mfvanek.pg.spring;

import io.github.mfvanek.pg.connection.PgConnection;
import io.github.mfvanek.pg.connection.PgConnectionImpl;
import io.github.mfvanek.pg.index.maintenance.IndexMaintenanceOnHostImpl;
import io.github.mfvanek.pg.index.maintenance.IndexesMaintenanceOnHost;
import io.github.mfvanek.pg.table.maintenance.TablesMaintenanceOnHost;
import io.github.mfvanek.pg.table.maintenance.TablesMaintenanceOnHostImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration(proxyBeanMethods = false)
@ConditionalOnClass({DataSource.class, PgConnection.class, IndexesMaintenanceOnHost.class, TablesMaintenanceOnHost.class})
@AutoConfigureAfter({DataSourceAutoConfiguration.class})
public class DatabaseStructureHealthAutoConfiguration {

  @Bean
  @ConditionalOnBean(name = "dataSource")
  @ConditionalOnMissingBean
  public PgConnection pgConnection(@Qualifier("dataSource") DataSource dataSource) {
    return PgConnectionImpl.ofPrimary(dataSource);
  }

  @Bean
  @ConditionalOnBean(PgConnection.class)
  @ConditionalOnMissingBean
  public IndexesMaintenanceOnHost indexesMaintenance(PgConnection pgConnection) {
    return new IndexMaintenanceOnHostImpl(pgConnection);
  }

  @Bean
  @ConditionalOnBean(PgConnection.class)
  @ConditionalOnMissingBean
  public TablesMaintenanceOnHost tablesMaintenance(PgConnection pgConnection) {
    return new TablesMaintenanceOnHostImpl(pgConnection);
  }
}

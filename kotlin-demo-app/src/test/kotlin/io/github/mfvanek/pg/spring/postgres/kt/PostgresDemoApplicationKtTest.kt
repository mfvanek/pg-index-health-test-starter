/*
 * Copyright (c) 2021-2024. Ivan Vakhrushev.
 * https://github.com/mfvanek/pg-index-health-test-starter
 *
 * This file is a part of "pg-index-health-test-starter".
 *
 * Licensed under the Apache License 2.0
 */

package io.github.mfvanek.pg.spring.postgres.kt

import com.zaxxer.hikari.HikariDataSource
import io.github.mfvanek.pg.connection.PgConnection
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import org.springframework.core.env.Environment
import org.springframework.test.context.ActiveProfiles

@ActiveProfiles("test")
@SpringBootTest
class PostgresDemoApplicationKtTest {

    @Autowired
    private lateinit var applicationContext: ApplicationContext

    @Autowired
    private lateinit var environment: Environment

    @Test
    fun contextLoadsAndDoesNotContainPgIndexHealthBeans() {
        assertThat(applicationContext.getBean("dataSource"))
            .isInstanceOf(HikariDataSource::class.java)

        assertThat(applicationContext.getBean("pgConnection"))
            .isInstanceOf(PgConnection::class.java)

        assertThat(environment.getProperty("spring.datasource.url"))
            .isNotBlank()
            .startsWith("jdbc:postgresql://localhost:")
            .endsWith("/demo_for_pg_index_health_starter?loggerLevel=OFF")
    }
}

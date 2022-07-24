/*
 * Copyright (c) 2021-2022. Ivan Vakhrushev.
 * https://github.com/mfvanek/pg-index-health-test-starter
 *
 * This file is a part of "pg-index-health-test-starter".
 *
 * Licensed under the Apache License 2.0
 */

package io.github.mfvanek.pg.spring;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DatabaseStructureHealthAutoConfigurationTest extends AutoConfigurationTestBase {

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
            .withInitializer(AutoConfigurationTestBase::initialize)
            .run(context -> {
                assertThat(context.getBeanDefinitionNames())
                    .filteredOn(beanNamesFilter)
                    .hasSameSizeAs(EXPECTED_BEANS)
                    .containsAll(EXPECTED_BEANS);
                assertThatBeansAreNotNullBean(context);
            });
    }

    @Test
    void shouldNotCreateAutoConfigurationWithDisabledProperty() {
        assertWithTestConfig()
            .withPropertyValues("pg.index.health.test.enabled=false")
            .withInitializer(AutoConfigurationTestBase::initialize)
            .run(context -> assertThat(context.getBeanDefinitionNames())
                .isNotEmpty()
                .filteredOn(beanNamesFilter)
                .isEmpty());
    }

    @Test
    void shouldCreateAutoConfigurationWhenPropertyExplicitlySet() {
        assertWithTestConfig()
            .withPropertyValues("pg.index.health.test.enabled=true")
            .withInitializer(AutoConfigurationTestBase::initialize)
            .run(context -> {
                assertThat(context.getBeanDefinitionNames())
                    .isNotEmpty()
                    .filteredOn(beanNamesFilter)
                    .hasSameSizeAs(EXPECTED_BEANS)
                    .containsAll(EXPECTED_BEANS);
                assertThatBeansAreNotNullBean(context);
            });
    }
}

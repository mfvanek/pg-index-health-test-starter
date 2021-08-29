/*
 * Copyright (c) 2021-2021. Ivan Vakhrushev.
 * https://github.com/mfvanek/pg-index-health-test-starter
 *
 * This file is a part of "pg-index-health-test-starter".
 *
 * Licensed under the Apache License 2.0
 */

package io.github.mfvanek.pg.spring;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DatabaseStructureHealthPropertiesTest {

    @Test
    void getterAndSetterShouldWork() {
        DatabaseStructureHealthProperties properties = new DatabaseStructureHealthProperties();
        assertThat(properties.isEnabled()).isTrue(); // default value

        properties.setEnabled(false);
        assertThat(properties.isEnabled()).isFalse();
    }
}

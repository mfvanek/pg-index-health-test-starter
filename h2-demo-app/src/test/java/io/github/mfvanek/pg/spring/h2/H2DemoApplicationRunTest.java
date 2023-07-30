/*
 * Copyright (c) 2021-2023. Ivan Vakhrushev.
 * https://github.com/mfvanek/pg-index-health-test-starter
 *
 * This file is a part of "pg-index-health-test-starter".
 *
 * Licensed under the Apache License 2.0
 */

package io.github.mfvanek.pg.spring.h2;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatCode;

class H2DemoApplicationRunTest {

    @Test
    void applicationShouldRun() {
        assertThatCode(() -> H2DemoApplication.main(new String[]{}))
            .doesNotThrowAnyException();
    }
}

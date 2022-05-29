/*
 * Copyright (c) 2021-2022. Ivan Vakhrushev.
 * https://github.com/mfvanek/pg-index-health-test-starter
 *
 * This file is a part of "pg-index-health-test-starter".
 *
 * Licensed under the Apache License 2.0
 */

package io.github.mfvanek.pg.spring;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Represents properties for managing pg-index-health-test-starter configuration.
 *
 * @author Ivan Vakhrushev
 * @since 2021.08.29
 */
@ConfigurationProperties(prefix = "pg.index.health.test")
public class DatabaseStructureHealthProperties {

    /**
     * Allows to manually disable starter even it presents on classpath.
     */
    private boolean enabled = true;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(final boolean enabled) {
        this.enabled = enabled;
    }
}

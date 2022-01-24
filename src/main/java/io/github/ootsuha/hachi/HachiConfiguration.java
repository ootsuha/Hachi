package io.github.ootsuha.hachi;

import io.github.ootsuha.hachi.core.*;
import org.springframework.boot.context.properties.*;
import org.springframework.context.annotation.*;

/**
 * Sets up config from application.yml.
 */
@Configuration
@ConfigurationProperties(value = "hachi")
public class HachiConfiguration extends HachiConfig {
}

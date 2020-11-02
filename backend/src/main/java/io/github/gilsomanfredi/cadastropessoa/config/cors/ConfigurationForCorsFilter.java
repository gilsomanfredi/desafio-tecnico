package io.github.gilsomanfredi.cadastropessoa.config.cors;

import javax.servlet.Filter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigurationForCorsFilter {

    @Bean
    Filter configCorsFilter() {

        return new ComponentCorsFilter();
    }
}

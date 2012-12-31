package org.bricket.b4.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;

@Configuration
public class PropertyPlaceholderConfig {
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
	PropertySourcesPlaceholderConfigurer pspc = new PropertySourcesPlaceholderConfigurer();
	pspc.setLocation(new ClassPathResource("/META-INF/spring/b4.properties"));
	pspc.setIgnoreUnresolvablePlaceholders(true);
	return pspc;
    }
}

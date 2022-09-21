package com.kapsta.sectorpicker.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan({"com.kapsta.sectorpicker"})
@EntityScan(basePackages = "com.kapsta.sectorpicker.model")
@EnableJpaRepositories("com.kapsta.sectorpicker.repository")
public class DataConfig {
}

package com.kapsta.sectorpickerback.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan({"com.kapsta.sectorpickerback"})
@EntityScan(basePackages = "com.kapsta.sectorpickerback.model")
@EnableJpaRepositories("com.kapsta.sectorpickerback.repo")
public class DataConfig {
}

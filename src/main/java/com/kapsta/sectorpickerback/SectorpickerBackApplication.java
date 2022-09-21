package com.kapsta.sectorpickerback;

import com.kapsta.sectorpickerback.config.WebExceptionHandling;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(WebExceptionHandling.class)
public class SectorpickerBackApplication {

	public static void main(String[] args) {
		SpringApplication.run(SectorpickerBackApplication.class, args);
	}

}

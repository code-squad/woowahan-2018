package com.woowahan.woowahan2018;

import com.woowahan.woowahan2018.support.ErrorMessageContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class Woowahan2018Application {

	public static void main(String[] args) {
		SpringApplication.run(Woowahan2018Application.class, args);
	}
}

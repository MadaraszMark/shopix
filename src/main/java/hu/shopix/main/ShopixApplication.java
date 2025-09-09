package hu.shopix.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ShopixApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShopixApplication.class, args);
	}

}

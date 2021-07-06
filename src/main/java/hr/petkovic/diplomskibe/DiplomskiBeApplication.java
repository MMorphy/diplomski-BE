package hr.petkovic.diplomskibe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages= {"hr.petkovic.diplomskibe.entity"})
@EnableJpaRepositories(basePackages= {"hr.petkovic.diplomskibe.repository"})
public class DiplomskiBeApplication {

	public static void main(String[] args) {
		SpringApplication.run(DiplomskiBeApplication.class, args);
	}

}

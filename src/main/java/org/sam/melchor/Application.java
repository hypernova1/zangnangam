package org.sam.melchor;

import org.sam.melchor.domain.Account;
import org.sam.melchor.domain.Category;
import org.sam.melchor.repository.AccountRepository;
import org.sam.melchor.repository.CategoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.ArrayList;
import java.util.Arrays;

@SpringBootApplication
@EnableJpaAuditing
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	CommandLineRunner insertDB(AccountRepository accountRepository, CategoryRepository categoryRepository) {
		return args -> {
			Account account = new Account();
			account.setEmail("chtlstjd01@gmail.com");
			account.setPassword("1111");
			account.setName("sam");

			accountRepository.save(account);

			Category category1 = new Category();
			category1.setName("자바");
			Category category2 = new Category();
			category2.setName("자바스크립트");
			Category category3 = new Category();
			category3.setName("DB");

			categoryRepository.saveAll(Arrays.asList(category1, category2, category3));

		};
	}

}

package org.sam.melchor;

import org.sam.melchor.domain.Account;
import org.sam.melchor.repository.AccountRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	CommandLineRunner initDatabase(AccountRepository accountRepository) {
		return args ->  {
			Account account = new Account();
			account.setEmail("chtlstjd01@gmail.com");
			account.setName("sam");
			account.setPassword("1111");
			accountRepository.save(account);
		};
	}

}

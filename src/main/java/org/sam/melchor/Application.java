package org.sam.melchor;

import org.sam.melchor.domain.*;
import org.sam.melchor.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
@EnableJpaAuditing
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	CommandLineRunner insertDB(AccountRepository accountRepository,
							   CategoryRepository categoryRepository,
							   PostRepository postRepository,
							   CommentRepository commentRepository,
							   RoleRepository roleRepository,
							   PasswordEncoder passwordEncoder) {
		return args -> {
			Role admin = new Role();
			admin.setName(RoleName.ROLE_ADMIN);
			Role user = new Role();
			user.setName(RoleName.ROLE_USER);
			roleRepository.saveAll(Arrays.asList(admin, user));

			Set<Role> role = new HashSet<>();
			role.add(admin);
			Account account = new Account();
			account.setEmail("chtlstjd01@gmail.com");
			account.setPassword(passwordEncoder.encode("1111"));
			account.setName("sam");
			account.setRoles(role);

			accountRepository.save(account);

			Category category1 = Category.builder()
					.orderNo(0)
					.name("자바")
					.path("java")
					.role("all")
					.build();
			Category category2 = Category.builder()
					.orderNo(1)
					.name("자바스크립트")
					.path("js")
					.role("all")
					.build();
			Category category3 = Category.builder()
					.orderNo(2)
					.name("DB")
					.path("db")
					.role("all")
					.build();

			categoryRepository.saveAll(Arrays.asList(category1, category2, category3));

			for(int i = 0; i <= 10; i++) {
				Post post = Post.builder()
						.category(category1)
						.title("post " + i)
						.content("hello " + i)
						.writer(account)
						.build();
				postRepository.save(post);

				for (int j = 0; j < 5; j++) {
					Comment comment = Comment.builder()
							.content("comment " + j)
							.writer(account)
							.post(post)
							.build();
					commentRepository.save(comment);
				}
			}

		};
	}

}

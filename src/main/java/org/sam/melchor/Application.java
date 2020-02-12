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

			Category category1 = new Category();
			category1.setOrderNo(0);
			category1.setName("자바");
			category1.setPath("java");
			category1.setRole("all");
			Category category2 = new Category();
			category2.setOrderNo(1);
			category2.setName("자바스크립트");
			category2.setPath("js");
			category2.setRole("all");
			Category category3 = new Category();
			category3.setOrderNo(2);
			category3.setName("DB");
			category3.setPath("db");
			category3.setRole("all");

			categoryRepository.saveAll(Arrays.asList(category1, category2, category3));

			for(int i = 0; i <= 10; i++) {
				Post post = new Post();
				post.setCategory(category1);
				post.setTitle("post" + i);
				post.setContent("hello" + i);
				post.setWriter(account);
				post.setLikeCnt(0);
				postRepository.save(post);

				for (int j = 0; j < 5; j++) {
					Comment comment = new Comment();
					comment.setContent("Comment" + j);
					comment.setWriter(account);
					comment.setPost(post);
					commentRepository.save(comment);
				}
			}

		};
	}

}

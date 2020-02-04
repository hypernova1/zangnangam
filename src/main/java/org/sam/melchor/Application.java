package org.sam.melchor;

import org.sam.melchor.domain.Account;
import org.sam.melchor.domain.Category;
import org.sam.melchor.domain.Comment;
import org.sam.melchor.domain.Post;
import org.sam.melchor.repository.AccountRepository;
import org.sam.melchor.repository.CategoryRepository;
import org.sam.melchor.repository.CommentRepository;
import org.sam.melchor.repository.PostRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Arrays;

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
							   CommentRepository commentRepository) {
		return args -> {
			Account account = new Account();
			account.setEmail("chtlstjd01@gmail.com");
			account.setPassword("1111");
			account.setName("sam");

			accountRepository.save(account);

			Category category1 = new Category();
			category1.setName("자바");
			category1.setPath("java");
			Category category2 = new Category();
			category2.setName("자바스크립트");
			category2.setPath("js");
			Category category3 = new Category();
			category3.setName("DB");
			category3.setPath("db");

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

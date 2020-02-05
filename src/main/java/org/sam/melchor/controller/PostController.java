package org.sam.melchor.controller;

import lombok.RequiredArgsConstructor;
import org.sam.melchor.domain.Account;
import org.sam.melchor.domain.Category;
import org.sam.melchor.domain.Post;
import org.sam.melchor.exception.AccountNotFoundException;
import org.sam.melchor.exception.CategoryNotFoundException;
import org.sam.melchor.exception.PostNotFoundException;
import org.sam.melchor.payload.PostListResponse;
import org.sam.melchor.payload.PostRequest;
import org.sam.melchor.repository.AccountRepository;
import org.sam.melchor.repository.CategoryRepository;
import org.sam.melchor.repository.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@CrossOrigin("http://localhost:3000")
public class PostController {

    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;
    private final AccountRepository accountRepository;

    @GetMapping("/{category}")
    public ResponseEntity<PostListResponse> getPostList(@RequestParam(required = false) Map<String, Object> searchRequest,
                                                  @PathVariable String category,
                                                  @RequestParam int page,
                                                  @RequestParam(defaultValue = "10") int size
                                                  ) {

        /*Map<PostSpecs.SearchKey, Object> searchKeys = new HashMap<>();

        for (String key : searchRequest.keySet()) {
            searchKeys.put(PostSpecs.SearchKey.valueOf(key.toUpperCase()), searchRequest.get(key));
        }*/
        Category byPath = categoryRepository.findByPath(category)
                .orElseThrow(() -> new CategoryNotFoundException(category));
        Page<Post> posts = postRepository.findByCategoryId(byPath.getId(), PageRequest.of(page - 1, size, Sort.Direction.DESC, "id"));

        PostListResponse response = new PostListResponse();
        response.setCategoryName(byPath.getName());
        response.setPostList(posts.getContent());
        response.setNext(posts.hasNext());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{categoryPath}/{id}")
    public ResponseEntity<Post> getPostDetail(@PathVariable String categoryPath,
                                              @PathVariable Long id) {

        Category category = categoryRepository.findByPath(categoryPath)
                .orElseThrow(() -> new CategoryNotFoundException(categoryPath));

        Post post = postRepository.findByCategoryAndId(category, id)
                .orElseThrow(() -> new PostNotFoundException(id));
        return ResponseEntity.ok(post);
    }

    @PostMapping("/post")
    public ResponseEntity<Post> createPost(@Valid @RequestBody PostRequest postRequest) {

        Post post = makePost(postRequest);

        Post savedPost = postRepository.save(post);

        URI location = ServletUriComponentsBuilder
            .fromCurrentRequest().path("/{id}")
            .buildAndExpand(savedPost.getId()).toUri();

        return ResponseEntity.created(location).body(savedPost);
    }

    @PutMapping("/post/{id}")
    public ResponseEntity<Post> updatePost(@Valid @RequestBody PostRequest postRequest,
                                           @PathVariable Long id) {
        postRequest.setId(id);
        Post post = makePost(postRequest);

        Post savedPost = postRepository.save(post);

        return ResponseEntity.ok(savedPost);
    }

    @Transactional
    @DeleteMapping("/post/{id}")
    public ResponseEntity<Boolean> removePost(@PathVariable Long id,
                                              String categoryPath) {

        Category category = categoryRepository.findByPath(categoryPath)
                .orElseThrow(() -> new CategoryNotFoundException(categoryPath));

        Long removeCnt = postRepository.deleteByIdAndCategory(id, category);

        if (removeCnt == 0) {
            throw new CategoryNotFoundException(categoryPath);
        }

        return ResponseEntity.ok().build();
    }

    private Post makePost(PostRequest postRequest) {

        Category category = categoryRepository.findById(postRequest.getCategory())
                .orElseThrow(() -> new CategoryNotFoundException(postRequest.getCategory()));

        Account account = accountRepository.findByEmail(postRequest.getWriter())
                .orElseThrow(() -> new AccountNotFoundException(postRequest.getWriter()));

        Post post = null;

        if (StringUtils.isEmpty(postRequest.getId())) {
            post = Post.set(postRequest, account, category);
            return post;
        }

        post = postRepository.findById(postRequest.getId())
                .orElseThrow(() -> new PostNotFoundException(postRequest.getId()));

        Post.modify(post, postRequest, account, category);

        return post;
    }

}

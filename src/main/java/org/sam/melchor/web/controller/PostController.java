package org.sam.melchor.web.controller;

import lombok.RequiredArgsConstructor;
import org.sam.melchor.domain.Account;
import org.sam.melchor.domain.Category;
import org.sam.melchor.domain.Post;
import org.sam.melchor.exception.AccountNotFoundException;
import org.sam.melchor.exception.CategoryNotFoundException;
import org.sam.melchor.exception.PostNotFoundException;
import org.sam.melchor.web.payload.PostResponse;
import org.sam.melchor.web.payload.PostsResponse;
import org.sam.melchor.web.payload.PostRequest;
import org.sam.melchor.repository.AccountRepository;
import org.sam.melchor.repository.CategoryRepository;
import org.sam.melchor.repository.PostRepository;
import org.sam.melchor.security.AuthUser;
import org.sam.melchor.security.UserPrincipal;
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
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;
    private final AccountRepository accountRepository;

    @GetMapping("/{categoryPath}")
    public ResponseEntity<PostsResponse> getPostList(@RequestParam(required = false) Map<String, Object> searchRequest,
                                                     @PathVariable String categoryPath,
                                                     @RequestParam int page,
                                                     @RequestParam(defaultValue = "10") int size
                                                  ) {

        /*Map<PostSpecs.SearchKey, Object> searchKeys = new HashMap<>();

        for (String key : searchRequest.keySet()) {
            searchKeys.put(PostSpecs.SearchKey.valueOf(key.toUpperCase()), searchRequest.get(key));
        }*/
        Category byPath = categoryRepository.findByPath(categoryPath)
                .orElseThrow(() -> new CategoryNotFoundException(categoryPath));
        Page<Post> posts = postRepository.findByCategoryId(byPath.getId(), PageRequest.of(page - 1, size, Sort.Direction.DESC, "id"));

        PostsResponse response = new PostsResponse();
        response.setCategoryName(byPath.getName());
        response.set(posts.getContent());
        response.setNext(posts.hasNext());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{categoryPath}/{id}")
    public ResponseEntity<?> getPostDetail(@PathVariable String categoryPath,
                                              @PathVariable Long id) {

        Category category = categoryRepository.findByPath(categoryPath)
                .orElseThrow(() -> new CategoryNotFoundException(categoryPath));

        Post post = postRepository.findByCategoryAndId(category, id)
                .orElseThrow(() -> new PostNotFoundException(id));

        PostResponse postResponse = getPostResponse(post);

        return ResponseEntity.ok(postResponse);
    }

    @PostMapping
    public ResponseEntity<?> createPost(@Valid @RequestBody PostRequest postRequest) {

        Post post = makePost(postRequest);
        Post savedPost = postRepository.save(post);

        URI location = ServletUriComponentsBuilder
            .fromCurrentRequest().path("/{id}")
            .buildAndExpand(savedPost.getId()).toUri();

        PostResponse postResponse = getPostResponse(savedPost);

        return ResponseEntity.created(location).body(postResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePost(@Valid @RequestBody PostRequest postRequest,
                                           @PathVariable Long id) {
        postRequest.setId(id);
        Post post = makePost(postRequest);

        Post savedPost = postRepository.save(post);
        PostResponse postResponse = getPostResponse(savedPost);
        return ResponseEntity.ok(postResponse);
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> removePost(@PathVariable Long id,
                                              @AuthUser UserPrincipal userPrincipal) {

        Account writer = accountRepository.findById(userPrincipal.getId())
                .orElseThrow(() -> new AccountNotFoundException(userPrincipal.getEmail()));
        Long removeCnt = postRepository.deleteByIdAndWriter(id, writer);

        if (removeCnt == 0) throw new PostNotFoundException(id);
        return ResponseEntity.ok().build();
    }

    private Post makePost(PostRequest postRequest) {

        Category category = categoryRepository.findById(postRequest.getCategoryId())
                .orElseThrow(() -> new CategoryNotFoundException(postRequest.getCategoryId()));

        Account account = accountRepository.findByEmail(postRequest.getWriter())
                .orElseThrow(() -> new AccountNotFoundException(postRequest.getWriter()));

        Post post;

        if (StringUtils.isEmpty(postRequest.getId())) {
            post = Post.set(postRequest, account, category);
            return post;
        }

        post = postRepository.findByIdAndWriter(postRequest.getId(), account)
                .orElseThrow(() -> new PostNotFoundException(postRequest.getId()));

        Post.modify(post, postRequest, account, category);

        return post;
    }

    private PostResponse getPostResponse(Post post) {
        PostResponse postResponse = new PostResponse();
        postResponse.set(post);
        return postResponse;
    }

}

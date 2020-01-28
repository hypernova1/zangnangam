package org.sam.melchor.controller;

import lombok.AllArgsConstructor;
import org.sam.melchor.domain.Category;
import org.sam.melchor.domain.Post;
import org.sam.melchor.exception.CategoryNotFoundException;
import org.sam.melchor.exception.PostNotFoundException;
import org.sam.melchor.payload.PostListResponse;
import org.sam.melchor.repository.CategoryRepository;
import org.sam.melchor.repository.PostRepository;
import org.sam.melchor.repository.specs.PostSpecs;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@AllArgsConstructor
@CrossOrigin("http://localhost:3000")
public class PostController {

    private PostRepository postRepository;
    private CategoryRepository categoryRepository;

    @GetMapping("{category}/{page}")
    public ResponseEntity<PostListResponse> getPostList(@RequestParam(required = false) Map<String, Object> searchRequest,
                                                  @PathVariable String category,
                                                  @PathVariable int page,
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

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> viewDetail(@PathVariable Long id) {

        Optional<Post> byId = postRepository.findById(id);

        return ResponseEntity.ok(byId.orElseThrow(() -> new PostNotFoundException(id)));
    }

    @PostMapping
    public ResponseEntity<Post> createPost(@Valid @RequestBody Post post) {

        Post savedPost = postRepository.save(post);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedPost.getId()).toUri();

        return ResponseEntity.created(location).body(savedPost);
    }

    @PutMapping
    public ResponseEntity<Post> updatePost(@Valid @RequestBody Post post) {
        Post savedPost = postRepository.save(post);
        return ResponseEntity.ok(savedPost);
    }

}

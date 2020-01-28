package org.sam.melchor.controller;

import lombok.AllArgsConstructor;
import org.sam.melchor.domain.Post;
import org.sam.melchor.exception.PostNotFoundException;
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
@RequestMapping("/post")
@AllArgsConstructor
public class PostController {

    private PostRepository postRepository;

    @GetMapping("/list")
    public ResponseEntity<Page<Post>> getPostList(@RequestParam(required = false) Map<String, Object> searchRequest,
                                                  @RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "10") int size) {

        Map<PostSpecs.SearchKey, Object> searchKeys = new HashMap<>();

        for (String key : searchRequest.keySet()) {
            searchKeys.put(PostSpecs.SearchKey.valueOf(key.toUpperCase()), searchRequest.get(key));
        }

        Page<Post> posts = searchRequest.isEmpty()
                ? this.postRepository.findAll(PageRequest.of(page, size, Sort.Direction.DESC, "id"))
                : this.postRepository.findAll(PostSpecs.searchWith(searchKeys), PageRequest.of(page, size, Sort.Direction.DESC, "id"));
        return ResponseEntity.ok(posts);
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

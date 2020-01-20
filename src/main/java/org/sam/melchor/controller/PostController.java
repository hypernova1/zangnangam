package org.sam.melchor.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.sam.melchor.domain.Post;
import org.sam.melchor.exception.PostNotFoundException;
import org.sam.melchor.repository.PostRepository;
import org.sam.melchor.repository.specs.PostSpecs;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@AllArgsConstructor
public class PostController {

    private ObjectMapper objectMapper;

    private PostRepository postRepository;

    @GetMapping("/post/list")
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

    @GetMapping("/post/{id}")
    public ResponseEntity<Post> viewDetail(@PathVariable Long id) {

        Optional<Post> byId = postRepository.findById(id);

        return ResponseEntity.ok(byId.orElseThrow(() -> new PostNotFoundException(id)));
    }

}

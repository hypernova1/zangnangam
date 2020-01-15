package org.sam.melchor.controller;

import lombok.AllArgsConstructor;
import org.sam.melchor.domain.Post;
import org.sam.melchor.repository.PostRepository;
import org.sam.melchor.repository.specs.PostSpecs;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
public class PostController {

    private PostRepository postRepository;

    @GetMapping("/post/list")
    public ResponseEntity<Page<Post>> getPostList(Map<String, Object> searchRequest,
                                                  @RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "10") int size) {

        Map<PostSpecs.SearchKey, Object> searchKeys = new HashMap<>();

        Page<Post> posts = searchRequest.isEmpty()
                ? this.postRepository.findAll(PageRequest.of(page, size, Sort.Direction.DESC, "id"))
                : this.postRepository.findAll(PostSpecs.searchWith(searchKeys), PageRequest.of(page, size, Sort.Direction.DESC, "id"));

        return ResponseEntity.ok(posts);
    }

}

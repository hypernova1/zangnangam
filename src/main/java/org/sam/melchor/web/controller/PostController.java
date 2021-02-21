package org.sam.melchor.web.controller;

import lombok.RequiredArgsConstructor;
import org.sam.melchor.config.security.AuthUser;
import org.sam.melchor.domain.Account;
import org.sam.melchor.service.PostService;
import org.sam.melchor.web.payload.PostDto;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/{categoryPath}")
    public ResponseEntity<?> getPostList(
            @PathVariable String categoryPath,
            @RequestParam int page,
            @RequestParam(defaultValue = "10") int size) {

        PostDto.ListResponse postList = postService.getPostList(categoryPath, page, size);
        return ResponseEntity.ok(postList);
    }

    @GetMapping("/{categoryPath}/{id}")
    public ResponseEntity<?> getPostDetail(@PathVariable String categoryPath, @PathVariable Long id) {
        PostDto.DetailResponse postDto = postService.getPost(id);
        return ResponseEntity.ok(postDto);
    }

    @PostMapping
    public ResponseEntity<?> createPost(
            @Valid @RequestBody PostDto.RegisterRequest request,
            @AuthUser Account account) {

        PostDto.DetailResponse postDto = postService.registerPost(request, account);

        URI location = ServletUriComponentsBuilder
            .fromCurrentRequest().path("/{id}")
            .buildAndExpand(postDto.getId()).toUri();

        return ResponseEntity.created(location).body(postDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePost(@Valid @RequestBody PostDto.UpdateRequest request,
                                           @PathVariable Long id) {
        postService.updatePost(id, request);
        PostDto.DetailResponse postDto = postService.getPost(id);
        return ResponseEntity.ok(postDto);
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> removePost(@PathVariable Long id,
                                              @AuthUser Account account) {
        postService.deletePost(id, account);
        return ResponseEntity.ok().build();
    }

}

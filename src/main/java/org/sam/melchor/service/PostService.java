package org.sam.melchor.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.sam.melchor.domain.Account;
import org.sam.melchor.domain.Category;
import org.sam.melchor.domain.Post;
import org.sam.melchor.exception.CategoryNotFoundException;
import org.sam.melchor.exception.PostNotFoundException;
import org.sam.melchor.repository.CategoryRepository;
import org.sam.melchor.repository.PostRepository;
import org.sam.melchor.web.payload.CommentDto;
import org.sam.melchor.web.payload.PostDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {

    private final PostRepository posts;
    private final CategoryRepository categories;
    private final ModelMapper modelMapper;

    public PostDto.ListResponse getPostList(String categoryPath, int page, int size) {
        Category category = categories.findByPath(categoryPath)
                .orElseThrow(() -> new CategoryNotFoundException(categoryPath));
        Page<Post> postList = posts.findByCategoryId(
                category.getId(), PageRequest.of(page - 1, size, Sort.Direction.DESC, "id")
        );

        List<PostDto.SummaryResponse> postDtoList = postList.stream()
                .map(post -> modelMapper.map(post, PostDto.SummaryResponse.class))
                .collect(Collectors.toList());

        PostDto.ListResponse response = new PostDto.ListResponse();
        response.setCategoryName(category.getName());
        response.setPostList(postDtoList);
        response.setNext(postList.hasNext());

        return response;
    }

    @Transactional
    public PostDto.DetailResponse registerPost(PostDto.RegisterRequest request, Account account) {
        Category category = categories.findById(request.getCategoryId())
                .orElseThrow(() -> new CategoryNotFoundException(request.getCategoryId()));

        Post post = Post.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .category(category)
                .writer(account)
                .build();
        Post savedPost = posts.save(post);
        return modelMapper.map(savedPost, PostDto.DetailResponse.class);
    }


    @Transactional
    public void updatePost(Long id, PostDto.UpdateRequest request) {
        Category category = categories.findById(request.getCategoryId())
                .orElseThrow(() -> new CategoryNotFoundException(request.getCategoryId()));
        Post post = posts.findById(id).orElseThrow(() -> new PostNotFoundException(id));
        post.update(request, category);
    }

    public PostDto.DetailResponse getPost(Long id) {
        Post post = posts.findById(id).orElseThrow(() -> new PostNotFoundException(id));
        PostDto.DetailResponse postDto = modelMapper.map(post, PostDto.DetailResponse.class);
        List<CommentDto.Response> commentList = post.getComments()
                .stream()
                .map(comment -> modelMapper.map(comment, CommentDto.Response.class))
                .collect(Collectors.toList());

        postDto.setCommentList(commentList);
        return postDto;
    }

    public void deletePost(Long id, Account account) {
        Long result = posts.deleteByIdAndWriter(id, account);
        if (result == 0) throw new PostNotFoundException(id);
    }
}


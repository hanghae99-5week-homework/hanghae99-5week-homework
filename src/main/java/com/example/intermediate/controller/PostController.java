package com.example.intermediate.controller;

import com.example.intermediate.dto.request.PostRequestDto;
import com.example.intermediate.dto.response.ResponseDto;
import com.example.intermediate.service.PostService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@RequiredArgsConstructor
@RestController
public class PostController {

  private final PostService postService;

  @RequestMapping(value = "/api/auth/posts", method = RequestMethod.POST)
  public ResponseDto<?> createPost(@RequestBody PostRequestDto requestDto,
      HttpServletRequest request) {
    return postService.createPost(requestDto, request);
  }

  @RequestMapping(value = "/api/posts/{postId}", method = RequestMethod.GET)
  public ResponseDto<?> getPost(@PathVariable Long postId) {
    return postService.getPost(postId);
  }

  @RequestMapping(value = "/api/posts", method = RequestMethod.GET)
  public ResponseDto<?> getAllPosts() {
    return postService.getAllPost();
  }

  @RequestMapping(value = "/api/auth/posts/{postId}", method = RequestMethod.PUT)
  public ResponseDto<?> updatePost(@PathVariable Long postId, @RequestBody PostRequestDto postRequestDto,
      HttpServletRequest request) {
    return postService.updatePost(postId, postRequestDto, request);
  }

  @RequestMapping(value = "/api/auth/posts/{postId}", method = RequestMethod.DELETE)
  public ResponseDto<?> deletePost(@PathVariable Long postId,
      HttpServletRequest request) {
    return postService.deletePost(postId, request);
  }
//MultipartHttpServletRequest
}

package com.example.intermediate.dto.response;

import com.example.intermediate.domain.Comment;
import com.example.intermediate.domain.Post;
import com.example.intermediate.domain.SubComment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MyPageResponseDto {
    private List<PostResponseDto> postResponseDtoList;
    private List<CommentResponseDto> commentResponseDtoList;
    private List<SubCommentResponseDto> subCommentResponseDtoList;
}

package com.example.intermediate.dto.response;

import com.example.intermediate.domain.Post;
import com.example.intermediate.domain.SubComment;
import com.example.intermediate.domain.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LikeResponseDto {
    private List<PostResponseDto> likePostResponseDtoList;
    private List<CommentResponseDto> likeCommentResponseDtoList;
    private List<SubCommentResponseDto> likeSubCommentResponseDtoList;
}

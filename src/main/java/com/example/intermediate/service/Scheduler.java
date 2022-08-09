package com.example.assignment3.service;

import com.example.assignment3.domain.Post;
import com.example.assignment3.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@RequiredArgsConstructor
@Component
public class Scheduler {

    private final PostService postService;
    private final PostRepository postRepository;

    @Scheduled(cron = "0 08 14 * * *")
    @Transactional
    public void deleteNoCommentPost() throws InterruptedException {
        log.info("댓글이 없는 게시물을 삭제합니다.");
        List<Post> postList = postRepository.findAll();

        for (int i=0; i<postList.size(); i++) {
            TimeUnit.SECONDS.sleep(1);

            Post p = postList.get(i);

            if (p.getComments().size() == 0) {
                postService.deleteNoCommentPost(p.getId());
            }
        }
    }
}

package com.example.demo.post.controller.response;

import com.example.demo.post.domain.Post;
import com.example.demo.user.domain.User;
import com.example.demo.user.domain.UserStatus;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


public class PostResponseTest {

    @Test
    public void Post으로_응답을_생성할_수_있다(){
        //given
        Post post = Post.builder()
                .content("helloworld")
                .writer(User.builder()
                        .email("pulpul8282@naver.com")
                        .nickname("pulpul8282")
                        .address("seoul")
                        .status(UserStatus.ACTIVE)
                        .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab")
                        .build())
                .build();

        //when
        PostResponse postResponse = PostResponse.from(post);

        //then
        assertThat(postResponse.getContent()).isEqualTo("helloworld");
        assertThat(postResponse.getWriter().getEmail()).isEqualTo("pulpul8282@naver.com");
        assertThat(postResponse.getWriter().getNickname()).isEqualTo("pulpul8282");
        assertThat(postResponse.getWriter().getStatus()).isEqualTo(UserStatus.ACTIVE);
    }
}

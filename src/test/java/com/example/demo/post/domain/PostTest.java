package com.example.demo.post.domain;

import com.example.demo.mock.TestClockHolder;
import com.example.demo.user.domain.User;
import com.example.demo.user.domain.UserStatus;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import java.util.UUID;

public class PostTest {

    @Test
    public void PostCreate로_게시물을_생성한다(){
        //given
        PostCreate postCreate = PostCreate.builder()
                .writerId(1)
                .content("helloworld")
                .build();
        User writer = User.builder()
                .email("pulpul8282@naver.com")
                .nickname("pulpul8282")
                .address("seoul")
                .status(UserStatus.ACTIVE)
                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab")
                .build();

        //when
        Post post = Post.from(writer, postCreate, new TestClockHolder(1678530673958L));

        //then
        assertThat(post.getContent()).isEqualTo("helloworld");
        assertThat(post.getWriter().getEmail()).isEqualTo("pulpul8282@naver.com");
        assertThat(post.getWriter().getNickname()).isEqualTo("pulpul8282");
        assertThat(post.getWriter().getAddress()).isEqualTo("seoul");
        assertThat(post.getWriter().getCertificationCode()).isEqualTo("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab");
        assertThat(post.getCreatedAt()).isEqualTo(1678530673958L);
        assertThat(post.getModifiedAt()).isEqualTo(1678530673958L);
    }

    @Test
    public void PostUpdate로_게시물을_수정한다(){
        //given
        PostUpdate postUpdate = PostUpdate.builder()
                .content("yjy")
                .build();
        User writer = User.builder()
                .email("pulpul8282@naver.com")
                .nickname("pulpul8282")
                .address("seoul")
                .status(UserStatus.ACTIVE)
                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab")
                .build();

        Post post = Post
                .builder()
                .id(1L)
                .content("helloworld")
                .createdAt(1678530673958L)
                .modifiedAt(0L)
                .writer(writer)
                .build();
        //when
        post = post.update(postUpdate ,new TestClockHolder(1678530673958L));

        //then
        assertThat(post.getContent()).isEqualTo("yjy");
        assertThat(post.getModifiedAt()).isEqualTo(1678530673958L);
    }
}

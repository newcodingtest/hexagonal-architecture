package com.example.demo.post.service;


import com.example.demo.mock.*;
import com.example.demo.post.domain.Post;
import com.example.demo.post.domain.PostCreate;
import com.example.demo.post.domain.PostUpdate;
import com.example.demo.user.domain.User;
import com.example.demo.user.domain.UserStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;



public class PostServiceImplTest {

    private PostServiceImpl postServiceImpl;

    @BeforeEach
    void init(){
        FakePostRepository fakePostRepository = new FakePostRepository();
        FakeUserRepository fakeUserRepository = new FakeUserRepository();

        this.postServiceImpl = PostServiceImpl.builder()
                .postRepository(fakePostRepository)
                .userRepository(fakeUserRepository)
                .clockHolder(new TestClockHolder(1678530673958L))
                .build();

       User user1 = User
               .builder()
               .id(1L)
               .email("pulpul8282@naver.com")
               .nickname("pulpul8282")
               .address("seoul")
               .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa")
               .status(UserStatus.ACTIVE)
               .lastLoginAt(0L)
               .build();
       User user2 = User
               .builder()
               .id(2L)
               .email("pulpul9292@naver.com")
               .nickname("pulpul9292")
               .address("seoul")
               .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab")
               .status(UserStatus.PENDING)
               .lastLoginAt(0L)
               .build();

        fakeUserRepository.save(user1);
        fakeUserRepository.save(user2);

        fakePostRepository.save(Post
                .builder()
                .id(1L)
                .content("helloworld")
                .createdAt(1678530673958L)
                .modifiedAt(0L)
                .writer(user1)
                .build());
    }

    @Test
    void getById는_존재하는_게시물을_가져온다(){
        //given
        Post result = postServiceImpl.getById(1);

        //when

        //then
        assertThat(result.getContent()).isEqualTo("helloworld");
        assertThat(result.getWriter().getEmail()).isEqualTo("pulpul8282@naver.com");
    }

    @Test
    void postCreateDto를_이용하여_게시물을_생성할_수_있다(){
        //given
        PostCreate postCreate = PostCreate.builder()
                .writerId(1)
                .content("test")
                .build();

        //when
        Post result = postServiceImpl.create(postCreate);

        //then
        assertThat(result.getId()).isNotNull();
        assertThat(result.getContent()).isEqualTo("test");
        assertThat(result.getCreatedAt()).isEqualTo(1678530673958L);
    }

    @Test
    void postUpdateDto를_이용하여_게시물을_수정할_수_있다(){
        //given
        PostUpdate postUpdate = PostUpdate.builder()
                .content("helloworld")
                .build();

        //when
        postServiceImpl.update(1,postUpdate);

        //then
        Post result = postServiceImpl.getById(1);
        assertThat(result.getContent()).isEqualTo("helloworld");
        assertThat(result.getModifiedAt()).isEqualTo(1678530673958L);
    }

}

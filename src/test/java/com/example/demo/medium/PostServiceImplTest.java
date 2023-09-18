package com.example.demo.medium;


import com.example.demo.post.controller.port.PostService;
import com.example.demo.post.domain.Post;
import com.example.demo.post.domain.PostCreate;
import com.example.demo.post.domain.PostUpdate;
import com.example.demo.post.service.PostServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@SpringBootTest
@TestPropertySource("classpath:/application-test.properties")
@SqlGroup({
        @Sql(value = "/sql/post-service-test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(value = "/sql/delete-all-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
public class PostServiceImplTest {

    @Autowired
    private PostService postService;

    @Test
    void getById는_존재하는_게시물을_가져온다(){
        //given
        Post result = postService.getById(1);

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
        Post result = postService.create(postCreate);

        //then
        assertThat(result.getId()).isNotNull();
        assertThat(result.getContent()).isEqualTo("test");
        assertThat(result.getCreatedAt()).isGreaterThan(0);
    }

    @Test
    void postUpdateDto를_이용하여_게시물을_수정할_수_있다(){
        //given
        PostUpdate postUpdate = PostUpdate.builder()
                .content("helloworld")
                .build();

        //when
        postService.update(1,postUpdate);

        //then
        Post result = postService.getById(1);
        assertThat(result.getContent()).isEqualTo("helloworld");
        assertThat(result.getModifiedAt()).isGreaterThan(0);
    }

}

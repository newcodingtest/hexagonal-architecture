package com.example.demo.user.controller;


import com.example.demo.mock.TestContainer;
import com.example.demo.user.controller.response.UserResponse;
import com.example.demo.user.domain.*;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


public class UserCreateControllerTest {

    @Test
    void 사용자는_회원_가입을_할_수있고_회원가입된_사용자는_PENDING_상태이다(){
        // given
        TestContainer testContainer = TestContainer.builder()
                .uuidHolder(() ->"aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab")
                .build();

        UserCreate userCreate = UserCreate.builder()
                .email("pulpul8282@naver.com")
                .nickname("pulpul8282")
                .address("pangyo")
                .build();

        // when
        ResponseEntity<UserResponse> result = testContainer.userCreateController.createUser(userCreate);

        // then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(201));
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().getEmail()).isEqualTo("pulpul8282@naver.com");
        assertThat(result.getBody().getNickname()).isEqualTo("pulpul8282");
        assertThat(result.getBody().getLastLoginAt()).isNull();
        assertThat(result.getBody().getId()).isEqualTo(1);
        assertThat(result.getBody().getStatus()).isEqualTo(UserStatus.PENDING);
        assertThat(testContainer.userRepository.getById(1).getCertificationCode()).isEqualTo("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab");
    }
}

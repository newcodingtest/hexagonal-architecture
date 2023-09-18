package com.example.demo.medium;

import com.example.demo.post.controller.response.PostResponse;
import com.example.demo.post.domain.Post;
import com.example.demo.user.domain.MyProfileResponse;
import com.example.demo.user.domain.User;
import com.example.demo.user.domain.UserStatus;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class UserResponseTest {

    @Test
    public void User으로_응답을_생성할_수_있다(){
        //given
        User user = User.builder()
                    .email("pulpul8282@naver.com")
                    .nickname("pulpul8282")
                    .address("seoul")
                    .status(UserStatus.ACTIVE)
                    .lastLoginAt(100L)
                    .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab")
                    .build();

        //when
        MyProfileResponse myProfileResponse = MyProfileResponse.from(user);

        //then
        assertThat(myProfileResponse.getEmail()).isEqualTo("pulpul8282@naver.com");
        assertThat(myProfileResponse.getNickname()).isEqualTo("pulpul8282");
        assertThat(myProfileResponse.getAddress()).isEqualTo("seoul");
        assertThat(myProfileResponse.getStatus()).isEqualTo(UserStatus.ACTIVE);
        assertThat(myProfileResponse.getLastLoginAt()).isEqualTo(100L);
    }
}

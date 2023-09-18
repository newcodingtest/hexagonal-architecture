package com.example.demo.user.domain;

import com.example.demo.common.exception.CertificationCodeNotMatchedException;
import com.example.demo.mock.TestClockHolder;
import com.example.demo.mock.TestUuidHolder;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class UserTest {

    @Test
    public void User는_UserCreate_객체로_생성할_수_있다(){
        //given
        UserCreate userCreate = UserCreate.builder()
                .email("pulpul8282@naver.com")
                .nickname("pulpul8282")
                .address("seoul")
                .build();
        //when
        User user = User.from(userCreate, new TestUuidHolder("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab"));

        //then
        assertThat(user.getId()).isNull();
        assertThat(user.getEmail()).isEqualTo("pulpul8282@naver.com");
        assertThat(user.getNickname()).isEqualTo("pulpul8282");
        assertThat(user.getAddress()).isEqualTo("seoul");
        assertThat(user.getStatus()).isEqualTo(UserStatus.PENDING);
        assertThat(user.getCertificationCode()).isEqualTo("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab");
    }


    @Test
    public void User는_UserUpdate_객체로_업데이트_할_수_있다(){
        //given
        User user = User.builder()
                .id(1L)
                .email("pulpul8282@naver.com")
                .nickname("pulpul8282")
                .address("seoul")
                .status(UserStatus.ACTIVE)
                .lastLoginAt(100L)
                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab")
                .build();
        UserUpdate userUpdate = UserUpdate.builder()
                .nickname("pulpul8282-n")
                .address("pangyo")
                .build();

        //when
        user = user.update(userUpdate);

        //then
        assertThat(user.getId()).isEqualTo(1L);
        assertThat(user.getEmail()).isEqualTo("pulpul8282@naver.com");
        assertThat(user.getAddress()).isEqualTo("pangyo");
        assertThat(user.getStatus()).isEqualTo(UserStatus.ACTIVE);
        assertThat(user.getLastLoginAt()).isEqualTo(100L);
        assertThat(user.getNickname()).isEqualTo("pulpul8282-n");
        assertThat(user.getAddress()).isEqualTo("pangyo");
    }

    @Test
    public void User는_로그인_할_수있고_로그인시_마지막_로그인_시간이_변경된다(){
        //given
        User user = User.builder()
                .id(1L)
                .email("pulpul8282@naver.com")
                .nickname("pulpul8282")
                .address("seoul")
                .status(UserStatus.ACTIVE)
                .lastLoginAt(100L)
                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab")
                .build();
        //when
        user = user.login(new TestClockHolder(1678594512345L));

        //then
        assertThat(user.getLastLoginAt()).isEqualTo(1678594512345L);
    }

    @Test
    public void User는_유효한_인증_코드로_계정을_활성화_할_수_있다(){
        //given
        User user = User.builder()
                .id(1L)
                .email("pulpul8282@naver.com")
                .nickname("pulpul8282")
                .address("seoul")
                .status(UserStatus.PENDING)
                .lastLoginAt(100L)
                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab")
                .build();
        //when
       user = user.certificate("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab");

        //then
        assertThat(user.getStatus()).isEqualTo(UserStatus.ACTIVE);
    }

    @Test
    public void User는_잘못된_인증_코드로_계정을_활성화_하려면_에러를_던진다(){
        //given
        User user = User.builder()
                .id(1L)
                .email("pulpul8282@naver.com")
                .nickname("pulpul8282")
                .address("seoul")
                .status(UserStatus.PENDING)
                .lastLoginAt(100L)
                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab")
                .build();

        //when
        //then
        assertThatThrownBy(() -> {
            user.certificate("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaac");
        }).isInstanceOf(CertificationCodeNotMatchedException.class);
    }


}

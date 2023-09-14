package com.example.demo.user.service;

import com.example.demo.common.exception.CertificationCodeNotMatchedException;
import com.example.demo.common.exception.ResourceNotFoundException;
import com.example.demo.mock.FakeMailSender;
import com.example.demo.mock.FakeUserRepository;
import com.example.demo.mock.TestClockHolder;
import com.example.demo.mock.TestUuidHolder;
import com.example.demo.user.domain.User;
import com.example.demo.user.domain.UserCreate;
import com.example.demo.user.domain.UserStatus;
import com.example.demo.user.domain.UserUpdate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;

public class UserServiceTest {

    private UserService userService;

    @BeforeEach
    void init(){
        FakeMailSender fakeMailSender = new FakeMailSender();
        FakeUserRepository fakeUserRepository = new FakeUserRepository();
        this.userService = UserService.builder()
                .uuidHolder(new TestUuidHolder("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab"))
                .clockHolder(new TestClockHolder(1679530673958L))
                .userRepository(new FakeUserRepository())
                .certificationService(new CertificationService(fakeMailSender))
                .build();
        fakeUserRepository.save(User
                .builder()
                .id(1L)
                .email("pulpul8282@naver.com")
                .nickname("pulpul8282")
                .address("seoul")
                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab")
                .status(UserStatus.ACTIVE)
                .lastLoginAt(0L)
                .build());

        fakeUserRepository.save(User
                .builder()
                .id(1L)
                .email("pulpul9292@naver.com")
                .nickname("pulpul9292")
                .address("seoul")
                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab")
                .status(UserStatus.PENDING)
                .lastLoginAt(0L)
                .build());
    }

    @Test
    void getByEmail은_ACTIVE_상태인_유저를_찾아올_수_있다(){
        //given
        String email = "pulpul8282@naver.com";

        //when
        User result = userService.getByEmail(email);

        //then
        assertThat(result.getNickname()).isEqualTo("pulpul8282");
    }

    @Test
    void getByEmail은_PENDING_상태인_유저는_찾아올_수_없다(){
        //given
        String email = "pulpul8282@naver.com";

        //when
        //then
        assertThatThrownBy(() -> {
            userService.getByEmail(email);
        }).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void getById은_ACTIVE_상태인_유저는_찾아올_수_있다(){
        //given
        //when
        User result = userService.getById(1);

        //then
        assertThat(result.getNickname()).isEqualTo("pulpul8282");
    }

    @Test
    void getById은_PENDING_상태인_유저는_찾아올_수_없다(){
        //given
        //when
        //then
        assertThatThrownBy(() -> {
            userService.getById(2);
        }).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void userCreate_를_이용하여_유저를_생성할_수_있다(){
        //given
        UserCreate userCreate = UserCreate.builder()
                .email("pulpul8282@naver.com")
                .address("seoul")
                .nickname("pulpul8282")
                .build();

        //when
        User result = userService.create(userCreate);

        //then
        assertThat(result.getId()).isNotNull();
        assertThat(result.getStatus()).isEqualTo(UserStatus.PENDING);
        assertThat(result.getCertificationCode()).isEqualTo("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab");

    }

    @Test
    void userUpdate_를_이용하여_유저를_수정할_수_있다(){
        //given
        UserUpdate userUpdate = UserUpdate.builder()
                .address("seoul")
                .nickname("pulpul8282-u")
                .build();

        //when
        userService.update(1,userUpdate);

        //then
        User result = userService.getById(1);
        assertThat(result.getId()).isNotNull();
        assertThat(result.getAddress()).isEqualTo("seoul");
        assertThat(result.getAddress()).isEqualTo("pulpul8282-u");
    }

    @Test
    void user를_로그인_시키면_마지막_로그인_시간이_변경된다(){
        //given
        //when
        userService.login(1);

        //then
        User result = userService.getById(1);
        assertThat(result.getLastLoginAt()).isEqualTo(1679530673958L);
    }

    @Test
    void PENDING_상태의_사용자는_인증_코드로_ACTIVE_시킬_수_있다(){
        //given
        //when
        userService.verifyEmail(2,"aaaa-a-a-a-aaab");

        //then
        User result = userService.getById(1);
        assertThat(result.getStatus()).isEqualTo(UserStatus.ACTIVE);
    }

    @Test
    void PENDING_상태의_사용자는_잘못된_인증_코드를_받으면_에러를_던진다(){
        //given
        //when
        //then
        assertThatThrownBy(() -> {
            userService.verifyEmail(2,"aaaa-a-a-a-aaab");
        }).isInstanceOf(CertificationCodeNotMatchedException.class);
    }


}

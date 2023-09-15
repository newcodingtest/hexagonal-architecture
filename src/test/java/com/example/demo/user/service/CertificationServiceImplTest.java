package com.example.demo.user.service;

import com.example.demo.mock.FakeMailSender;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class CertificationServiceImplTest {

    @Test
    public void 이메일과_컨텐츠가_제대로_만들어져_보내진다(){
        //given
        FakeMailSender fakeMailSender = new FakeMailSender();
        CertificationServiceImpl certificationServiceImpl = new CertificationServiceImpl(fakeMailSender);

        //when
        certificationServiceImpl.send("pulpul8282@naver.com",1,"aaaaaaa-aa-aa-aa-aaaaaa");

        //then
        assertThat(fakeMailSender.email).isEqualTo("pulpul8282@naver.com");
        assertThat(fakeMailSender.title).isEqualTo("Please certify your email address");
    }
}

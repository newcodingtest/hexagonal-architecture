package com.example.demo.user.controller.request;

import com.example.demo.user.domain.UserUpdate;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class UserUpdateRequest {

    private final String nickname;
    private final String address;

    @Builder
    public UserUpdateRequest(@JsonProperty("nickname") String nickname,
                             @JsonProperty("address") String address){
        this.nickname = nickname;
        this.address = address;
    }

    public UserUpdate to(){
        return null;
    }

}

package com.example.demo.user.controller.port;

import com.example.demo.user.domain.User;

public interface UserReadService {
    User getById(long id);
    User getByEmail(String email);
}

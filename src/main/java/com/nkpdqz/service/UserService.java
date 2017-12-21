package com.nkpdqz.service;

import com.nkpdqz.domain.User;


public interface UserService {
    boolean register(String username,String password,String rePassword);
    User login(String username,String password);
    boolean isgetUsername(String username);
}

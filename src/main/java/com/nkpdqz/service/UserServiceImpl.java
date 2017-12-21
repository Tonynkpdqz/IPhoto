package com.nkpdqz.service;

import com.nkpdqz.dao.UserDao;
import com.nkpdqz.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    public boolean register(String username, String password, String rePassword) {
        if (!password.equals(rePassword)){
            return false;//这个最好放在前端检查
        } else {
            dao.register(username, password);
            System.out.println("working heihei");
            return true;
        }
    }

    public User login(String username, String password) {
        if (dao.findUserByUsernameAndPassword(username, password)!=null) {
            User user = dao.findUserByUsernameAndPassword(username, password);
            return user;
        } else {
            return null;
        }

    }

    @Override
    public boolean isgetUsername(String username) {
        String username1 = dao.getUsername(username);
        if (username1 == null){
            return true;
        }else {
            return false;
        }
    }

    @Autowired
    private UserDao dao;

    public void setDao(UserDao dao) {
        this.dao = dao;
    }

}

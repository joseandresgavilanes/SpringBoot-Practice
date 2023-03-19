package com.springboot.springboot.controllers;

import com.springboot.springboot.dao.UserDao;
import com.springboot.springboot.models.User;
import com.springboot.springboot.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    private UserDao userDao;

    @Autowired
    private JWTUtil jwtUtil;


    @RequestMapping(value = "api/login",  method = RequestMethod.POST)
    public String addUser(@RequestBody User user){

        User userLogged = userDao.verifyCredentials(user);

        if(userLogged != null){

            String tokenJWT = jwtUtil.create(String.valueOf(userLogged.getId()), userLogged.getEmail());
            return tokenJWT;
        }else{
            return  "FAIL";
        }

    }

}

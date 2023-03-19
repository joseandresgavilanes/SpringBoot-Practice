package com.springboot.springboot.controllers;


import com.springboot.springboot.dao.UserDao;
import com.springboot.springboot.models.User;
import com.springboot.springboot.utils.JWTUtil;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import de.mkammerer.argon2.Argon2Factory.Argon2Types;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserDao userDao;

    @Autowired
    private JWTUtil jwtUtil;

    @RequestMapping(value = "api/user",  method = RequestMethod.GET)
    public List<User> getUser(@RequestHeader(value = "Authorization") String token){

        if (!validateToken(token)) {
            return null;
        }

        return userDao.getUser();

    }


    private boolean validateToken(String token){
        String userId = jwtUtil.getKey(token);
        return userId != null;
    }

    @RequestMapping(value = "api/user",  method = RequestMethod.POST)
    public void addUser(@RequestBody User user){

        Argon2 argo2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        String hash = argo2.hash(1, 1024, 1, user.getPassword());
        user.setPassword(hash);
        userDao.addUser(user);

    }

    @RequestMapping(value = "api/user/{id}",method = RequestMethod.GET)
    public User getUserById(@PathVariable Long id){
        User user = new User();
        user.setId(id);
        user.setName("Jose");
        user.setSurname("Gavilanes");
        user.setEmail("jose@gmail.com");
        user.setPhone("0995426348");

        return user;
    }

    @RequestMapping(value = "api/user/{id}", method = RequestMethod.DELETE)
    public void deleteUser(@RequestHeader(value = "Authorization") String token, @PathVariable Long id){
        if (!validateToken(token)) {
            return;
        }


        userDao.delete(id);
    }


}

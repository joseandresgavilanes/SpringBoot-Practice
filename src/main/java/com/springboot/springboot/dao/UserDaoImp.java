package com.springboot.springboot.dao;

import com.springboot.springboot.models.User;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import de.mkammerer.argon2.Argon2Factory.Argon2Types;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import org.springframework.stereotype.Repository;


@Repository
@Transactional
public class UserDaoImp implements UserDao{


    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<User> getUser() {

        String query= "FROM User";
        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public void delete(Long id) {
        User user = entityManager.find(User.class, id);
            entityManager.remove(user);
    }

    @Override
    public void addUser(User user) {
        entityManager.merge(user);
    }


    @Override
    public User verifyCredentials (User user) {

        String query= "FROM User WHERE email= :email";
        List<User> list = entityManager.createQuery(query)
            .setParameter("email", user.getEmail())
            .getResultList();

        if(list.isEmpty()){
            return null;
        }

        String passwordHashed = list.get(0).getPassword();

        Argon2 argon2 = Argon2Factory.create(Argon2Types.ARGON2id);

         if(argon2.verify(passwordHashed, user.getPassword())){
             return list.get(0);
        }
         return null;

    }

}

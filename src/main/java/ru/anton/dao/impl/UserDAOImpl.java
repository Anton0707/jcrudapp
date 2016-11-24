package ru.anton.dao.impl;

import ru.anton.dao.UserDAO;
import ru.anton.entity.User;
import ru.anton.util.HibernateUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDAOImpl implements UserDAO{
    public UserDAOImpl() {
        System.out.println("UserDAOImpl");
    }

    @Autowired
    private HibernateUtil hibernateUtil;


    @Override
    public int createUser(User user) {
        return (int) hibernateUtil.create(user);
    }

    @Override
    public User updateUser(User user) {
        return hibernateUtil.update(user);
    }

    @Override
    public void deleteUser(int id) {
        User user = new User();
        user.setId(id);
        hibernateUtil.delete(user);
    }

    @Override
    public List<User> getAllUsers() {
        return hibernateUtil.fetchAll(User.class);
    }

    @Override
    public User getUser(int id) {
        return hibernateUtil.fetchById(id, User.class);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<User> getAllUsers(String userName) {
        String query = "SELECT t.* FROM Test t WHERE t.name like '%"+ userName +"%'";
        List<Object[]> userObjects = hibernateUtil.fetchAll(query);
        List<User> users = new ArrayList<User>();
        for(Object[] userObject: userObjects) {
            User user = new User();
            int id = (int) userObject[0];
            String name = (String) userObject[1];
            int age = (int) userObject[2];
            boolean isAdmin = (boolean) userObject[3];
            Timestamp timestamp = (Timestamp) userObject[4];
            user.setId(id);
            user.setName(name);
            user.setAge(age);
            user.setIsAdmin(isAdmin);
            user.setCreatedDate(timestamp);
            users.add(user);
        }
        System.out.println(users);
        return users;
    }
}

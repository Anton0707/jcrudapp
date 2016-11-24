package ru.anton.dao;

import ru.anton.entity.User;

import java.util.List;

public interface UserDAO {
    public int createUser(User user);
    public User updateUser(User user);
    public void deleteUser(int id);
    public List<User> getAllUsers();
    public User getUser(int id);
    public List<User> getAllUsers(String userName);
}

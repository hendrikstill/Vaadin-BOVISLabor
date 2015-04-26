package com.bovis;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class TestingUserDAO implements UserDAO, Serializable {

    static List<User> users;
    DatabaseHelper db;

    static {
        users = new LinkedList<User>();

    }

    public TestingUserDAO() {
        db = new DatabaseHelper();
    }



    @Override
    public User getUserBy(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username)
                    && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public User getUserBy(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public User loginWithNameAndPassword(String username, String password) {
        //returns a loginContainer (SQLContainer)
        db.loginWithUsernameAndPassword(username, password);

        //Create new User object with logged in user and return User object

        return null;
    }

    @Override
    public List<User> getFriendsForUser(User user) {
        return null;
    }

    @Override
    public boolean saveUser(User user) {
        try {
            if (users.contains(user)) {
                return true;
            } else {
                users.add(user);
                return true;
            }
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<User> getUsers() {
        return users;
    }

}
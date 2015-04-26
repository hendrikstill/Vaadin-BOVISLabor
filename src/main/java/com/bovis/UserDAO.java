package com.bovis;


import java.util.List;

public interface UserDAO {

    public User getUserBy(String username, String password);

    public User getUserBy(String username);

    public User loginWithNameAndPassword(String username, String password);

    public User registerUserWithNameAndPassword(String username, String password);

    public List<User> getFriendsForUser(User user);

    public boolean saveUser(User user);

    public List<User> getUsers();

    public void initDatabaseService();
}

package com.bovis;

import java.io.Serializable;

public class User implements Serializable {
    private final long id;

    private String username = null;
    private String password = null;
    private boolean isAdmin = false;
    private boolean isOnline = false;

    public User() {
        id = -1;
    }

    public User(long id, String username, String password, boolean isAdmin, boolean isOnline) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.isAdmin = isAdmin;
        this.isOnline = isOnline;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setIsOnline(boolean isOnline) {
        this.isOnline = isOnline;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public long getId() {
        return id;
    }

    public int hashCode() {
        return (int) id;
    }

    public boolean equals(Object o) {
        if (o instanceof User) {
            return ((User) o).getId() == getId();
        }
        return false;
    }
}

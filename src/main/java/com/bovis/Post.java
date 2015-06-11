package com.bovis;

import java.util.Date;

/**
 * Created by Sven-Eric on 5/7/15.
 */
public class Post {
    private final User owner;
    private final String created;
    private final String description;

    public Post(User owner, String created, String description) {
        this.owner = owner;
        this.created = created;
        this.description = description;
    }

    public User getOwner() {
        return owner;
    }

    public String getCreated() {
        return created;
    }

    public String getDescription() {
        return description;
    }
}

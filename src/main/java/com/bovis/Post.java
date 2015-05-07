package com.bovis;

import java.util.Date;

/**
 * Created by Sven-Eric on 5/7/15.
 */
public class Post {
    private final User owner;
    private final Date created;
    private final Content content;

    public Post(User owner, Date created, Content content) {
        this.owner = owner;
        this.created = created;
        this.content = content;
    }
}

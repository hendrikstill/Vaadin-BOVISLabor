package com.bovis;

import java.util.List;

/**
 * Created by Sven-Eric on 5/21/15.
 */
public class TimelineDAOImpl implements TimelineDAO {

    DatabaseHelper db;

    @Override
    public List<Post> getLatestPostsForUser(User user) {
        return db.getLatestPostsForUser(user);
    }

    @Override
    public void initDatabaseService() {
        db = new DatabaseHelper();
        db.init();
    }
}

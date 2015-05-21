package com.bovis;

import java.util.List;

/**
 * Created by Sven-Eric on 5/21/15.
 */
public interface TimelineDAO {

    public List<Post> getLatestPostsForUser(User user);

    public void initDatabaseService();
}

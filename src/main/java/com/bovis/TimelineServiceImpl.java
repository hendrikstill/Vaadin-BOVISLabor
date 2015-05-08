package com.bovis;

import java.util.List;

/**
 * Created by Sven-Eric on 5/7/15.
 */
public class TimelineServiceImpl implements TimelineService {
    @Override
    public List<Post> getLatestPostsForUser(User n) {
        /*
        TODO:Get posts for user
            * Get all friends for user n
            * Get all content from all friends
            * Sort content date (newest first)
         */
        return null;
    }

    @Override
    public List<User> getFriendsforUser(User currentUser) {
        /*
        TODO:Get friends for user
            * Get all friends
            * Sort them (online friends first)
         */
        return null;
    }
}

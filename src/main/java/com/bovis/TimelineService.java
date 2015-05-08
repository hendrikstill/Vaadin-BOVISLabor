package com.bovis;

import java.util.List;

/**
 * Created by Sven-Eric on 5/7/15.
 */
public interface TimelineService {

    public List<Post> getLatestPostsForUser(User n);

    public List<User> getFriendsforUser(User currentUser);
}

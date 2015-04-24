package com.bovis;

import com.vaadin.ui.UI;

import java.util.List;

public interface MessageService {

    public List<Message> getLatestMessages(User user1, User user2, int n);

    public void registerParticipant(User user, UI ui);

    public void unregisterParticipant(User user);

}

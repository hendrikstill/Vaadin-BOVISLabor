package com.bovis;

import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;

import javax.inject.Inject;

/**
 * Created by Sven-Eric on 5/7/15.
 */
@CDIView("TimelineView")
public class TimelineView extends CustomComponent implements View {

    @Inject
    private UserDAO userDAO;

    @Inject
    private UserInfo userInfo;

    @Inject
    private TimelineService timelineService;

    private static final int MAX_POSTS = 16;

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        Layout layout = buildMainLayout();
        setCompositionRoot(layout);
    }

    private Layout buildMainLayout() {
        /*
            HorizontalLayout (Main)
                add Panel
                add Panel
                add Panel
         */


        HorizontalLayout layout = new HorizontalLayout();
        layout.setWidth("100%");
        layout.setMargin(true);
        layout.setSpacing(true);
        layout.addComponent(buildNavigationLayout());
        layout.addComponent(buildTimeLineLayout());
        layout.addComponent(buildFriendlistLayout());
        return layout;
    }

    private Layout buildTimeLineLayout(){
        Layout layout = new VerticalLayout();
        layout.setWidth("60%");
        layout.addComponent(new Label("Timeline"));
        return layout;
    }

    private Layout buildNavigationLayout(){
        Layout layout = new VerticalLayout();
        layout.setWidth("20%");
        layout.addComponent(new Label("Navigation"));
        return layout;
    }

    private Layout buildFriendlistLayout(){
        Layout layout = new VerticalLayout();
        layout.setWidth("20%");
        layout.addComponent(new Label("Friendlist"));
        return layout;
    }
}

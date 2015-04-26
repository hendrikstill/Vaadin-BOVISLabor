package com.bovis;

import com.vaadin.cdi.CDIView;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

import javax.inject.Inject;

@CDIView("login")
public class LoginView extends CustomComponent implements View, ClickListener {

    @Inject
    private UserInfo user;

    @Inject
    private UserDAO userDAO;

    private TextField usernameField;
    private PasswordField passwordField;
    private Button loginButton;

    private Label helperLabel;

    private DatabaseHelper dbHelp = new DatabaseHelper();

    @Inject
    private javax.enterprise.event.Event<NavigationEvent> navigationEvent;

    @Override
    public void enter(ViewChangeEvent event) {


        usernameField = new TextField("Username");
        passwordField = new PasswordField("Password");
        loginButton = new Button("Login");
        loginButton.addClickListener(this);
        loginButton.setClickShortcut(KeyCode.ENTER);

//        User currentLoggedInUser = dbHelp.loginWithUsernameAndPassword("John Doe", "password");
//
//        if(currentLoggedInUser != null){
//            //logged in
//        }else {
//            //wrong username and/or password
//        }

        VerticalLayout layout = new VerticalLayout();
        setCompositionRoot(layout);
        layout.setSizeFull();
        layout.setMargin(true);
        layout.setSpacing(true);

        layout.addComponent(usernameField);
        layout.addComponent(passwordField);
        layout.addComponent(loginButton);

    }

    public DatabaseHelper getDbHelp(){
        return dbHelp;
    }



    @Override
    public void buttonClick(ClickEvent event) {
        String username = usernameField.getValue();
        String password = passwordField.getValue();

        userDAO.initDatabaseService();
        User loginUser = userDAO.getUserBy(username, password);
        if (loginUser == null) {
            new Notification("Wrong password", Notification.TYPE_ERROR_MESSAGE)
                    .show(getUI().getPage());
            return;
        }

        user.setUser(loginUser);
        DatabaseHelper db = new DatabaseHelper();

        navigationEvent.fire(new NavigationEvent("chat"));
    }
}
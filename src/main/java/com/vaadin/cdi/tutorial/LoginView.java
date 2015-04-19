package com.vaadin.cdi.tutorial;

import javax.inject.Inject;
import javax.xml.crypto.Data;

import com.vaadin.cdi.CDIView;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Label;

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


        helperLabel = new Label();
        String userName = dbHelp.getUserName(0);
        usernameField.setValue(userName);

        dbHelp.loginWithUsernameAndPassword("John Doe", "password");

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
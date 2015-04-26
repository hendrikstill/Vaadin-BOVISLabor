package com.bovis;

import com.vaadin.cdi.CDIView;
import com.vaadin.event.ShortcutAction;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Button;

import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

import javax.inject.Inject;


/**
 * Created by Sven-Eric on 4/26/15.
 */
@CDIView("register")
public class RegistrationView extends CustomComponent implements View, ClickListener {


    @Inject
    private UserInfo user;

    @Inject
    private UserDAO userDAO;

    @Inject
    private javax.enterprise.event.Event<NavigationEvent> navigationEvent;

    private TextField usernameField;
    private PasswordField passwordField;
    private PasswordField repeatPasswordField;
    private Button registrationButton;


    @Override
    public void buttonClick(ClickEvent clickEvent) {
        String username = usernameField.getValue();
        String password = passwordField.getValue();
        String repeatPassword = repeatPasswordField.getValue();

        if(!password.equals(repeatPassword)){
            new Notification("Passwords do not match! U dumb or wut?", Notification.TYPE_ERROR_MESSAGE)
                    .show(getUI().getPage());
            return;
        }


        userDAO.initDatabaseService();
        User registrationUser = userDAO.registerUserWithNameAndPassword(username, password);

        if (registrationUser == null) {
            new Notification("Registration failed", Notification.TYPE_ERROR_MESSAGE)
                    .show(getUI().getPage());
            return;
        }

        user.setUser(registrationUser);
        navigationEvent.fire(new NavigationEvent("chat"));
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {


        usernameField = new TextField("Username");
        passwordField = new PasswordField("Password");
        repeatPasswordField = new PasswordField("Verify Password");
        registrationButton = new Button("Register");
        registrationButton.addClickListener(this);
        registrationButton.setClickShortcut(ShortcutAction.KeyCode.ENTER);


        VerticalLayout layout = new VerticalLayout();
        setCompositionRoot(layout);
        layout.setSizeFull();
        layout.setMargin(true);
        layout.setSpacing(true);

        layout.addComponent(usernameField);
        layout.addComponent(passwordField);
        layout.addComponent(repeatPasswordField);
        layout.addComponent(registrationButton);

    }
}

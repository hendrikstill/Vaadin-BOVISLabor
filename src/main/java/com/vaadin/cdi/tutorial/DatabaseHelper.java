package com.vaadin.cdi.tutorial;


import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.data.util.sqlcontainer.connection.JDBCConnectionPool;
import com.vaadin.data.util.sqlcontainer.connection.SimpleJDBCConnectionPool;
import com.vaadin.data.util.sqlcontainer.query.FreeformQuery;

import java.sql.SQLException;
import java.util.Objects;

/**
 * Created by Sven-Eric on 3/29/15.
 */
public class DatabaseHelper {

    private JDBCConnectionPool connectionPool = null;

    private SQLContainer personContainer = null;
    private SQLContainer userContainer = null;

    public DatabaseHelper(){
        initConnectionPool();
        initConnections();
    }

    private void initConnectionPool() {
        try {
            connectionPool = new SimpleJDBCConnectionPool(
                    "org.hsqldb.jdbc.JDBCDriver","jdbc:hsqldb:file:/Users/Sven-Eric/Downloads/cdi-tutorial-master/TestDB","SA","", 2, 5);


        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    private void initConnections() {
        try {
//            TableQuery q1 = new TableQuery("PERSONADDRESS",connectionPool);
//            q1.setVersionColumn("VERSION");
//            personContainer = new SQLContainer(q1);



            FreeformQuery query = new FreeformQuery("SELECT * FROM USER",connectionPool,"ID");
            userContainer = new SQLContainer(query);

        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public SQLContainer getPersonContainer() {
        return personContainer;
    }

    public SQLContainer getUserContainer() {
        return userContainer;
    }

    public boolean loginWithUsernameAndPassword(String username, String password){
        try {
            FreeformQuery query = new FreeformQuery("SELECT ID FROM USER WHERE NAME="+username, connectionPool, "ID");
            userContainer = new SQLContainer(query);
        } catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }


    public String getUserName(int userId) {
        Object cityItemId = userContainer.getIdByIndex(userId);
        return userContainer.getItem(cityItemId).getItemProperty("NAME")
                .getValue().toString();
    }

    /**
     * Adds a new city to the container and commits changes to the database.
     *
     * @param cityName
     *            Name of the city to add
     * @return true if the city was added successfully
     */
    public boolean addCity(String cityName) {
        userContainer.getItem(userContainer.addItem()).getItemProperty("NAME")
                .setValue(cityName);
        try {
            userContainer.commit();
            return true;
        } catch (UnsupportedOperationException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


}

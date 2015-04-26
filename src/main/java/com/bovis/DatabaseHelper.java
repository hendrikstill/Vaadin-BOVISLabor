package com.bovis;


import com.google.gwt.thirdparty.guava.common.collect.Iterables;
import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.data.util.sqlcontainer.connection.JDBCConnectionPool;
import com.vaadin.data.util.sqlcontainer.connection.SimpleJDBCConnectionPool;
import com.vaadin.data.util.sqlcontainer.query.FreeformQuery;
import com.vaadin.data.util.sqlcontainer.query.TableQuery;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;


/**
 * Created by Sven-Eric on 3/29/15.
 */
public class DatabaseHelper {

    private JDBCConnectionPool connectionPool = null;

    private SQLContainer personContainer = null;
    private SQLContainer userContainer = null;
    private SQLContainer loginContainer = null;

    public DatabaseHelper(){

    }

    public void init(){
        initConnectionPool();
        initConnections();
    }

    private void initConnectionPool() {
        try {
            connectionPool = new SimpleJDBCConnectionPool(
                    "org.hsqldb.jdbc.JDBCDriver","jdbc:hsqldb:file:/Users/Sven-Eric/Uni/Semester/Master 2/BO Labor/Vaadin-BOVISLabor/TestDB","SA","", 2, 5);


        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    private void initConnections() {
        try {
//            TableQuery q1 = new TableQuery("PERSONADDRESS",connectionPool);
//            q1.setVersionColumn("VERSION");
//            personContainer = new SQLContainer(q1);



            TableQuery query = new TableQuery("USER",connectionPool);
            query.setVersionColumn("VERSION");
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

    public int loginWithUsernameAndPassword(String username, String password){
//        userContainer.addContainerFilter(new Compare.Equal("name",username));
        try {
            FreeformQuery query = new FreeformQuery("SELECT * FROM USER WHERE " +
                    "NAME='"+username+"' AND password='"+password+"'", connectionPool, "ID");
            loginContainer = new SQLContainer(query);
        } catch (SQLException e){
            e.printStackTrace();
        }



        Collection<?> itemIds = loginContainer.getItemIds();

        if(itemIds.size() == 1){
            Object o = Iterables.get(itemIds, 0);
            int id = Integer.valueOf(userContainer.getItem(o).getItemProperty("ID").getValue().toString());
            String lName = userContainer.getItem(o).getItemProperty("NAME").getValue().toString();
            String lPassword = userContainer.getItem(o).getItemProperty("PASSWORD").getValue().toString();
            boolean admin = Boolean.valueOf(
                    userContainer.
                            getItem(o).
                            getItemProperty("ADMIN").
                            getValue().
                            toString());
            boolean online = Boolean.valueOf(
                    userContainer.
                            getItem(o).
                            getItemProperty("ONLINE").
                            getValue().
                            toString());

            User user = new User(id, lName, lPassword, admin, online);

            String s = o.toString();
            return Integer.getInteger(s);
        } else {
            return -1;
        }
    }

    public User getUserById(int id){
        return null;
    }

    public List<User> getFriendListForUser(User user){
        return null;
    }

    public boolean setFriendForUser(User user, User friend){
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

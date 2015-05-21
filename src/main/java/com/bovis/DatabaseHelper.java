package com.bovis;


import com.google.gwt.thirdparty.guava.common.collect.Iterables;
import com.sun.org.apache.xpath.internal.operations.Bool;
import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.data.util.sqlcontainer.connection.JDBCConnectionPool;
import com.vaadin.data.util.sqlcontainer.connection.SimpleJDBCConnectionPool;
import com.vaadin.data.util.sqlcontainer.query.FreeformQuery;
import com.vaadin.data.util.sqlcontainer.query.TableQuery;

import com.vaadin.addon.sqlcontainer.RowItem;

import java.sql.SQLException;
import java.util.ArrayList;
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
    private SQLContainer friendlistContainer = null;

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

    public User register(String username, String password){

        Object rowId = userContainer.addItem();
            userContainer.getItem(rowId).
                    getItemProperty("NAME").
                    setValue(username);

            userContainer.getItem(rowId).
                    getItemProperty("PASSWORD").
                    setValue(password);

            userContainer.getItem(rowId).
                    getItemProperty("ADMIN").
                    setValue(0);

            userContainer.getItem(rowId).
                    getItemProperty("ONLINE").
                    setValue(0);

        try {
            userContainer.commit();

        } catch (UnsupportedOperationException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return loginWithUsernameAndPassword(username,password);
    }


    public User loginWithUsernameAndPassword(String username, String password){
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

            int admin = Integer.valueOf(userContainer.
                    getItem(o).
                    getItemProperty("ADMIN").getValue().toString());

            int online = Integer.valueOf(
                    userContainer.
                            getItem(o).
                            getItemProperty("ONLINE").
                            getValue().
                            toString());



            User user = new User(id, lName, lPassword, convertIntToBoolean(admin), convertIntToBoolean(online));

            return user;
        } else {
            return null;
        }
    }


    /*
    Genius method to convert Integer into Boolean.
    We are too stupid!
     */
    private Boolean convertIntToBoolean(int arg){
        if(arg == 1)
            return true;
        else
            return false;
    }

    private User convertObjectIntoUser(Object o){
        int id = Integer.valueOf(userContainer.getItem(o).getItemProperty("ID").getValue().toString());
        String lName = userContainer.getItem(o).getItemProperty("NAME").getValue().toString();
        String lPassword = userContainer.getItem(o).getItemProperty("PASSWORD").getValue().toString();

        int admin = Integer.valueOf(userContainer.
                getItem(o).
                getItemProperty("ADMIN").getValue().toString());

        int online = Integer.valueOf(
                userContainer.
                        getItem(o).
                        getItemProperty("ONLINE").
                        getValue().
                        toString());



        return new User(id, lName, lPassword, convertIntToBoolean(admin), convertIntToBoolean(online));
    }

    public User getUserById(int id){
        Object userItemId = userContainer.getIdByIndex(id);
        return convertObjectIntoUser(userItemId);
    }

    public List<User> getFriendListForUser(User user){
        List<User> friendList = new ArrayList<User>();
        try {
            FreeformQuery query = new FreeformQuery("SELECT * FROM FRIENDLIST WHERE " +
                    "USER_ID_1="+user.getId()+" AND CONFIRMED="+ 1 +"", connectionPool, "ID");
            friendlistContainer = new SQLContainer(query);
        } catch (SQLException e){
            e.printStackTrace();
        }

        Collection<?> itemIds = friendlistContainer.getItemIds();

        if(itemIds.size() > 0){
            for(int i = 0; i < itemIds.size(); i++){
                Object o = Iterables.get(itemIds, i);
                int id = Integer.valueOf(friendlistContainer.getItem(o).getItemProperty("USER_ID_2").getValue().toString());
                friendList.add(getUserById(id));
            }
        } else {
            return null;
        }

        return friendList;
    }

    public boolean setFriendForUser(User user, User friend){
        return false;
    }

    public String getUserName(int userId) {
        Object cityItemId = userContainer.getIdByIndex(userId);
        return userContainer.getItem(cityItemId).getItemProperty("NAME")
                .getValue().toString();
    }


    private void updateFriendlistContainer(){
        TableQuery query = new TableQuery("FRIENDLIST",connectionPool);
        query.setVersionColumn("VERSION");

        try {
            friendlistContainer = new SQLContainer(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public User confirmFriendForUser(User user, User friend){

        addFriendForUser(user, friend);

        /*
        TODO: need to confirm friend request for both data rows: 1:3 and 3:1
        Filter on friendlistContainer for IDs 1 and 3 and update CONFIRMED column to 1
        Alternative: get friendlistContainer of just this datarow with freeformquery
         */



        return null;
    }

    public User addFriendForUser(User user, User friend){
        updateFriendlistContainer();
        Object rowId = friendlistContainer.addItem();

        friendlistContainer.getItem(rowId).
                getItemProperty("USER_ID_1").
                setValue(user.getId());

        friendlistContainer.getItem(rowId).
                getItemProperty("USER_ID_2").
                setValue(friend.getId());

        try {
            friendlistContainer.commit();

        } catch (UnsupportedOperationException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

}

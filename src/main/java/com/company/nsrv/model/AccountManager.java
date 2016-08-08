package com.company.nsrv.model;


import com.company.nsrv.model.user.User;

import java.util.List;

public interface AccountManager {

    void addUser(User user);

    void removeUser(String username);

    User getUser(String username);

    boolean containsUser(String username);

    List<User> getUsers();

}

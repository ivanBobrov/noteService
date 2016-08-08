package com.company.nsrv.model;


import com.company.nsrv.model.user.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class AccountManagerImpl implements AccountManager {
    private final Map<String, User> accounts = new ConcurrentHashMap<String, User>();

    @Override
    public User getUser(String username) {
        return accounts.get(username);
    }

    @Override
    public void removeUser(String username) {
        if (accounts.containsKey(username)) {
            accounts.remove(username);
        }
    }

    @Override
    public void addUser(User user) {
        String username = user.getUsername();
        if (!accounts.containsKey(username)) {
            accounts.put(username, user);
        }
    }

    @Override
    public boolean containsUser(String username) {
        return accounts.containsKey(username);
    }

    @Override
    public List<User> getUsers() {
        return new ArrayList<User>(accounts.values());
    }
}

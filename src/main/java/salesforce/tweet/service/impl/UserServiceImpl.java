package salesforce.tweet.service.impl;

import salesforce.tweet.User;
import salesforce.tweet.service.UserService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserServiceImpl implements UserService {


    //Should never be global here as the service is supposed a thin layer and this map should be local to a method
    //and needs to be loaded when the call is being made.
    Map<Integer,User> userMap = new HashMap<>();

    private static UserService instance = new UserServiceImpl();

    public static UserService getInstance(){
        return instance;
    }

    private UserServiceImpl(){};

    @Override
    public boolean validateUser(int userId) {
        return userMap.keySet().stream().filter(user -> user == userId).findFirst().isPresent();
    }


    @Override
    public User getUser(int userId) {
        return userMap.get(userId);
    }

    @Override
    public void addUser(int userId){
        userMap.put(userId,new User(userId));
    }
}

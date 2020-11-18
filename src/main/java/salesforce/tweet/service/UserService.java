package salesforce.tweet.service;

import salesforce.tweet.User;

public interface UserService{
    boolean validateUser(int userId);

    User getUser(int userId);

    void addUser(int userId);
}

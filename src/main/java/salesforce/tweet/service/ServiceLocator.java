package salesforce.tweet.service;

import salesforce.tweet.service.impl.TweetHistoryServiceImpl;
import salesforce.tweet.service.impl.UserFollowerServiceImpl;
import salesforce.tweet.service.impl.UserServiceImpl;

public class ServiceLocator {
    public static UserService getUserService(){
        return UserServiceImpl.getInstance();
    }

    public static TweetHistoryService getTweetHistoryService(){
        return TweetHistoryServiceImpl.getInstance();
    }

    public static UserFollowerService getUserFollowerService(){
        return UserFollowerServiceImpl.getInstance();
    }
}

package salesforce.tweet.service.impl;

import salesforce.tweet.Tweet;
import salesforce.tweet.User;
import salesforce.tweet.service.ServiceLocator;
import salesforce.tweet.service.TweetHistoryService;
import salesforce.tweet.service.UserFollowerService;
import salesforce.tweet.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

public class UserFollowerServiceImpl implements UserFollowerService {

    private UserService userService = ServiceLocator.getUserService();

    private TweetHistoryService tweetHistoryService = ServiceLocator.getTweetHistoryService();

    private static UserFollowerService instance = new UserFollowerServiceImpl();

    public static UserFollowerService getInstance(){
        return instance;
    }

    private UserFollowerServiceImpl(){}

    @Override
    public void follow(int followerId, int userId) {
        //Adding a User as a follower
        if(!userService.validateUser(userId) || !userService.validateUser(followerId)){
            return; //TODO: Throw a defined exception.
        }
        //Get the userObject. The follower is to be added to this user Object.
        User user = userService.getUser(userId);
        user.getFollowing().add(followerId);
        //Get all the tweets for this user.
        List<Tweet> followerTweets =
                tweetHistoryService.getNewsFeed(followerId).stream().filter(tweet->tweet.getUserId() == followerId).collect(Collectors.toList());
        tweetHistoryService.postTweets(userId,followerTweets);
    }

    @Override
    public void unfollow(int followerId, int userId) {
        //Adding a User as a follower
        if(!userService.validateUser(userId) || !userService.validateUser(followerId)){
            return; //TODO: Throw a defined exception.
        }
        //Get the userObject. The follower is to be added to this user Object.
        User user = userService.getUser(userId);
        user.getFollowing().remove(Integer.valueOf(followerId));
        //Remove all tweets for the user and read to fill in the till we fill up to default value or more.
        tweetHistoryService.removeTweets(userId,followerId);
    }
}

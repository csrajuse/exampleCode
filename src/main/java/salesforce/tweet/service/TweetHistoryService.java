package salesforce.tweet.service;

import salesforce.tweet.Tweet;

import java.util.List;

public interface TweetHistoryService{
    List<Tweet> getNewsFeed(int userId);
    void postTweet(int userId,String tweetText);
    void postTweets(int userId,List<Tweet> tweets);
    void removeTweets(int userId,int followerRemoved);
}

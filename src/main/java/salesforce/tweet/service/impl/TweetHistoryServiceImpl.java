package salesforce.tweet.service.impl;

import salesforce.tweet.Tweet;
import salesforce.tweet.User;
import salesforce.tweet.service.ServiceLocator;
import salesforce.tweet.service.TweetHistoryService;
import salesforce.tweet.service.UserService;

import java.util.*;
import java.util.stream.Collectors;

public class TweetHistoryServiceImpl implements TweetHistoryService {

    private static final int DEFAULT_VALUE = 50;
    //Contains the Tweets of the user and the users he is following.
    private Map<Integer,List<Tweet>> userTweetMap = new HashMap<>();
    //If there is spring would have used
    private UserService userService = ServiceLocator.getUserService();

    private static TweetHistoryService instance = new TweetHistoryServiceImpl();

    public static TweetHistoryService getInstance(){
        return instance;
    }

    private TweetHistoryServiceImpl(){};

    @Override
    public List<Tweet> getNewsFeed(int userId) {
        List<Tweet> tweetList = userTweetMap.getOrDefault(userId,new ArrayList<>());
        if(tweetList.size() > 10){
            tweetList = tweetList.subList(0,10);
        }
        return tweetList;
    }

    @Override
    public void postTweet(int userId, String tweetText) {
        long timeOfInsert = new Date().getTime();
        Tweet tweet = new Tweet(userId,tweetText,timeOfInsert);
        List<Tweet> userTweets = userTweetMap.getOrDefault(userId,new ArrayList<>());
        userTweets.add(0,tweet);
        removeAfterSomeValue(userTweets);
        userTweetMap.put(userId,userTweets);
        //Now figure out all the followers of this user and add this tweet to them too.
        User user = userService.getUser(userId);
        user.getFollowing().stream().forEach(userIdFollowing->addTweet(userIdFollowing,tweet));

    }

    @Override
    public void postTweets(int userId, List<Tweet> tweets) {
        List<Tweet> userTweets = userTweetMap.getOrDefault(userId,new ArrayList<>());
        userTweets.addAll(tweets);
        Collections.sort(userTweets,new TweetComparator());
        removeAfterSomeValue(userTweets);
        userTweetMap.put(userId,userTweets);
    }

    @Override
    public void removeTweets(int userId, int followerRemoved) {
        List<Tweet> userTweets = userTweetMap.getOrDefault(userId,new ArrayList<>());
        List<Tweet> removedUserTweets = userTweets.stream().filter(tweet -> tweet.getUserId()!=followerRemoved).collect(Collectors.toList());
        //for all the remaining users we need to add more tweets so we can fill in the void if any caused.
        List<Integer> followers = userService.getUser(userId).getFollowing();
        followers.stream().forEach(followerUserId->removedUserTweets.addAll(userTweetMap.getOrDefault(followerUserId,new ArrayList<>())));
        Collections.sort(removedUserTweets,new TweetComparator());
        removeAfterSomeValue(removedUserTweets);
        userTweetMap.put(userId,removedUserTweets);
    }

    private void addTweet(int userId,Tweet tweet){
        List<Tweet> userTweets = userTweetMap.getOrDefault(userId,new ArrayList<>());
        userTweets.add(0,tweet);
        removeAfterSomeValue(userTweets);
        userTweetMap.put(userId,userTweets);
    }

    private void removeAfterSomeValue(List<Tweet> userTweets){
        if(userTweets.size()>DEFAULT_VALUE) {
            List<Tweet> toBeRemoved = userTweets.subList(DEFAULT_VALUE, userTweets.size() - 1);
            userTweets.removeAll(toBeRemoved);
        }
    }

    class TweetComparator implements Comparator<Tweet>{

        @Override
        public int compare(Tweet o1, Tweet o2) {
            return (int)(o2.getDatetime()-o1.getDatetime());
        }
    }
}

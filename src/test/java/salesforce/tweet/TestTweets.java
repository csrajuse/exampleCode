package salesforce.tweet;

import org.junit.Test;
import salesforce.tweet.service.ServiceLocator;
import salesforce.tweet.service.TweetHistoryService;
import salesforce.tweet.service.UserFollowerService;
import salesforce.tweet.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

public class TestTweets {

    @Test
    public void TestTweetFunctionality() throws InterruptedException {
        UserService userService = ServiceLocator.getUserService();
        UserFollowerService userFollowerService = ServiceLocator.getUserFollowerService();
        TweetHistoryService tweetHistoryService = ServiceLocator.getTweetHistoryService();

        userService.addUser(1);
        tweetHistoryService.postTweet(1,"Testing for user 1 tweet1");
        tweetHistoryService.postTweet(1,"Testing for user 1 tweet2");
        tweetHistoryService.postTweet(1,"Testing for user 1 tweet3");
        tweetHistoryService.postTweet(1,"Testing for user 1 tweet4");
        tweetHistoryService.postTweet(1,"Testing for user 1 tweet5");

        userService.addUser(2);
        tweetHistoryService.postTweet(2,"Testing for user 2 tweet1");
        tweetHistoryService.postTweet(2,"Testing for user 2 tweet2");
        tweetHistoryService.postTweet(2,"Testing for user 2 tweet3");
        tweetHistoryService.postTweet(2,"Testing for user 2 tweet4");
        tweetHistoryService.postTweet(2,"Testing for user 2 tweet5");

        Thread.sleep(1000);

        tweetHistoryService.postTweet(1,"Testing for user 1 tweet6");
        tweetHistoryService.postTweet(2,"Testing for user 2 tweet6");
        tweetHistoryService.postTweet(1,"Testing for user 1 tweet7");
        tweetHistoryService.postTweet(2,"Testing for user 2 tweet7");
        tweetHistoryService.postTweet(1,"Testing for user 1 tweet8");
        for(int i=10;i<100;i++){
            tweetHistoryService.postTweet(1,"Testing for user 1 tweet"+i);
            tweetHistoryService.postTweet(2,"Testing for user 2 tweet"+i);
            Thread.sleep(1000);
        }

        userFollowerService.follow(2,1);// User 2 following user 1
        userFollowerService.follow(1,2);// User 1 following user 2

        System.out.println(tweetHistoryService.getNewsFeed(1).stream().map(Tweet::getTweetText).collect(Collectors.toList()));
        System.out.println(tweetHistoryService.getNewsFeed(2).stream().map(Tweet::getTweetText).collect(Collectors.toList()));
        userFollowerService.unfollow(1,2);// User 1 following user 2
        System.out.println(tweetHistoryService.getNewsFeed(2).stream().map(Tweet::getTweetText).collect(Collectors.toList()));
    }
}

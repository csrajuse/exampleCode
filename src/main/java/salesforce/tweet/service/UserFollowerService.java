package salesforce.tweet.service;

public interface UserFollowerService {
    void follow(int followerId,int userId);
    void unfollow(int followerId, int userId);
}

package salesforce.tweet;

import java.util.ArrayList;
import java.util.List;

public class User{
    private int userId;
    private List<Integer> following;

    public User(int userId){
        this.userId = userId;
        this.following = new ArrayList<>();
    }

    public int getUserId() {
        return userId;
    }

    public List<Integer> getFollowing() {
        return following;
    }
}
package salesforce.tweet;

public class Tweet{
    private int userId;
    private String tweetText;
    private long datetime;

    public Tweet(int userId, String tweetText,long datetime){
        this.userId = userId;
        this.tweetText = tweetText;
        this.datetime = datetime;
    }

    public int getUserId() {
        return userId;
    }

    public String getTweetText() {
        return tweetText;
    }

    public long getDatetime() {
        return datetime;
    }
}

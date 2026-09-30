public class UserAudit {
    public String userId;
    public String action;

    public long getTimestamp() {
        return timestamp;
    }

    public String getAction() {
        return action;
    }

    public String getUserId() {
        return userId;
    }

    public long timestamp;
    public UserAudit(String userId, String action, long timestamp){
        this.userId = userId;
        this.action = action;
        this.timestamp = timestamp;
    }
}

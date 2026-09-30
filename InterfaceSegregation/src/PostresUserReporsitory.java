import java.util.List;

public class PostresUserReporsitory implements UserReader, UserWriter, UserAuditReader, UserAnalyticsReader{
    @Override
    public User findById(String id) {
        System.out.println("SELECT * FROM users WHERE id = "+id);
        return new User(id,"Alice");
    }
    public void save(User user){
        System.out.println("INSERT INTO users ...");
    }

    public void update(User user){
        System.out.println("UPDATE users ...");
    }

    public void delete(String id){
        System.out.println("DELETE FROM user ...");
    }

    @Override
    public List<User> findActiveUsers() {
        return List.of(new User("1","Alice"), new User("2","Bob"));
    }

    @Override
    public List<UserAudit> getAuditTrail(String userId) {
        return List.of(new UserAudit(userId, "LOGIN", 1000L));
    }

    @Override
    public AnalyticsReport getAnalytics(String query) {
        return new AnalyticsReport("results");
    }
}

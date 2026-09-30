import java.util.List;

public interface UserReader {
    User findById(String id);
    List<User> findActiveUsers();
}

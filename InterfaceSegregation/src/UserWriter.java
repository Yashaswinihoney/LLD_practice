public interface UserWriter {
    void save(User user);
    void update(User user);
    void delete(String id);
}

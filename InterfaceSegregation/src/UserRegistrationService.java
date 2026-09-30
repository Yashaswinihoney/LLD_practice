public class UserRegistrationService {
    private final UserWriter writer;
    public UserRegistrationService(UserWriter writer){
        this.writer=writer;
    }
    public void register(User user){
        writer.save(user);
    }
}

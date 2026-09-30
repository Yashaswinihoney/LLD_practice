public class UserAuthService {
    private final UserReader reader;
    public UserAuthService(UserReader reader){
        this.reader=reader;
    }
    public User autheticate(String id){
        return reader.findById(id);
    }
}

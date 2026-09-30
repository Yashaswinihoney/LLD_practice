// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        PostresUserReporsitory repo=new PostresUserReporsitory();
        UserAuthService auth=new UserAuthService(repo);
        auth.autheticate("42");
        UserRegistrationService reg=new UserRegistrationService(repo);
        reg.register(new User("99","Bob"));
        UserAdminService admin=new UserAdminService(repo);
        admin.showAudit("42");
    }
}
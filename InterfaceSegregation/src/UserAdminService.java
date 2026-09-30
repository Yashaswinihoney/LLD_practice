public class UserAdminService {
    private final UserAuditReader auditReader;
    public UserAdminService(UserAuditReader reader){
        this.auditReader=reader;
    }
    public void showAudit(String userId){
        auditReader.getAuditTrail(userId).forEach(a->System.out.println("Audit : "+ a));
    }
}

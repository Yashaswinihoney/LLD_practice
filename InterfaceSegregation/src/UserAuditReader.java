import java.util.List;

public interface UserAuditReader {
    List<UserAudit> getAuditTrail(String userId);
}


import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

/**
 * Session工具类
 *
 * @author Miller
 * @since 2024-12-14
 */
public class SessionUtils {
    public static String getUserId() {
        try {
            Subject subject = SecurityUtils.getSubject();
            //Session session = subject.getSession();
            Object principal = SecurityUtils.getSubject().getPrincipal();
            String user = String.valueOf(principal);
            assert user != null;
            return user;
        } catch (Exception e) {
            return null;
        }
    }
}
package cc.moonkin.microoj.util;

import cc.moonkin.microoj.data.SessionUser;
import cc.moonkin.microoj.data.enums.Role;

/**
 * @author anchun
 *
 */
public class SessionUserContext {
    private static final ThreadLocal<SessionUser> SESSION_USER_CONTEXT = new ThreadLocal<>();

    public static void setSessionUser(final SessionUser sessionUser) {
        SESSION_USER_CONTEXT.set(sessionUser);
    }

    public static SessionUser getSessionUser() {
        return SESSION_USER_CONTEXT.get();
    }

    public static Long getUserId() {
        final SessionUser sessionUser = getSessionUser();
        return sessionUser == null ? null : sessionUser.getId();
    }

    public static Role getUserRole() {
        final SessionUser sessionUser = getSessionUser();
        return sessionUser == null ? null : sessionUser.getRole();
    }

    public static void clear() {
        SESSION_USER_CONTEXT.remove();
    }
}

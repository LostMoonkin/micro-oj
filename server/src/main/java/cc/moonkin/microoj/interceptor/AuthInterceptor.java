package cc.moonkin.microoj.interceptor;

import cc.moonkin.microoj.annotation.Auth;
import cc.moonkin.microoj.data.enums.Role;
import cc.moonkin.microoj.log.MicroLog;
import cc.moonkin.microoj.util.SessionUserContext;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.lang.reflect.Method;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author anchun
 *
 */
@Component
public class AuthInterceptor implements HandlerInterceptor {

    private static final MicroLog LOG = MicroLog.getLogger(AuthInterceptor.class);

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response,
            final Object handler) throws Exception {
        final Auth auth;
        try {
            final Method method = ((HandlerMethod) handler).getMethod();
            auth = method.getAnnotation(Auth.class);
        } catch (final Exception e) {
            LOG.message("AuthInterceptor", e)
                    .error();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return false;
        }
        if (auth == null) {
            return true;
        }
        final Role sessionRole = SessionUserContext.getUserRole();
        if (sessionRole == null) {
            LOG.message("AuthInterceptor", "session user is null")
                    .with("require", auth.role())
                    .warn();
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
        if (sessionRole.lessThan(auth.role())) {
            LOG.message("AuthInterceptor", "not permitted")
                    .with("require", auth.role())
                    .with("sessionUser", SessionUserContext.getSessionUser())
                    .warn();
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return false;
        }
        return true;
    }
}

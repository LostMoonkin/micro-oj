package cc.moonkin.microoj.advice;

import cc.moonkin.microoj.annotation.Auth;
import cc.moonkin.microoj.data.enums.Role;
import cc.moonkin.microoj.exception.AuthenticationException;
import cc.moonkin.microoj.log.MicroLog;
import cc.moonkin.microoj.util.SessionUserContext;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author anchun
 *
 */
@Component
@Aspect
public class AuthenticationAdvice {

    private static final MicroLog LOG = MicroLog.getLogger(AuthenticationAdvice.class);

    @Pointcut("@annotation(cc.moonkin.microoj.annotation.Auth)")
    public void pointCut() {
    }

    @Before("pointCut()")
    public void authenticate(final JoinPoint point) throws AuthenticationException {
        final Method method = ((MethodSignature) point.getSignature()).getMethod();
        final Auth auth = method.getAnnotation(Auth.class);
        final Role sessionRole = SessionUserContext.getUserRole();
        if (sessionRole == null) {
            LOG.message("AuthInterceptor", "session user is null")
                    .with("require", auth.role())
                    .warn();
            throw new AuthenticationException();
        }
        if (sessionRole.lessThan(auth.role())) {
            LOG.message("AuthInterceptor", "not permitted")
                    .with("require", auth.role())
                    .with("sessionUser", SessionUserContext.getSessionUser())
                    .warn();
            throw new AuthenticationException();
        }
    }
}

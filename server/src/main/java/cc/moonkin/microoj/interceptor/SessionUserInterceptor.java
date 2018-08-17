package cc.moonkin.microoj.interceptor;

import cc.moonkin.microoj.constant.SessionConstants;
import cc.moonkin.microoj.util.JWTHelper;
import cc.moonkin.microoj.util.SessionUserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author anchun
 *
 */
@Component
public class SessionUserInterceptor implements HandlerInterceptor {

    @Autowired
    private JWTHelper jwtHelper;

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response,
            final Object handler) throws Exception {
        parserHeader(request);
        return true;
    }

    private void parserHeader(final HttpServletRequest request) {
        final String header = request.getHeader(SessionConstants.TOKEN_HEADER);

        if (header == null || !header.startsWith(SessionConstants.TOKEN_PREFIX)) {
            return;
        }

        final String token = header.substring(SessionConstants.TOKEN_PREFIX.length() + 1);

        SessionUserContext.setSessionUser(jwtHelper.parserToken(token));
    }
}

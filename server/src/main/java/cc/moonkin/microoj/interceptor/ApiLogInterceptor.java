package cc.moonkin.microoj.interceptor;

import cc.moonkin.microoj.constant.SessionConstants;
import cc.moonkin.microoj.log.MicroLog;
import cc.moonkin.microoj.util.IpUtil;
import cc.moonkin.microoj.util.SessionUserContext;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author anchun
 *
 */
@Component
public class ApiLogInterceptor implements HandlerInterceptor {

    public static final String CHARACTER_SET = "UTF-8";

    private static final MicroLog LOG = MicroLog.getLogger(ApiLogInterceptor.class);

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response,
            final Object handler) throws Exception {
        request.setAttribute(SessionConstants.START_TIME_ATTRIBUTE, System.currentTimeMillis());
        return true;
    }

    @Override
    public void postHandle(final HttpServletRequest request, final HttpServletResponse response,
            final Object handler,
            final ModelAndView modelAndView) throws Exception {
        final long startTime = (Long) request.getAttribute(SessionConstants.START_TIME_ATTRIBUTE);
        final long cost = System.currentTimeMillis() - startTime;
        String uri;
        String queryString;
        try {
            uri = URLDecoder.decode(request.getRequestURI(), CHARACTER_SET);
            queryString = URLDecoder.decode(request.getQueryString(), CHARACTER_SET);
        } catch (final Exception e) {
            uri = request.getRequestURI();
            queryString = request.getQueryString();
        }
        LOG.message("ApiLogInterceptor")
                .with("uri", uri)
                .with("queryString", queryString)
                .with("cost", cost)
                .with("sessionUser", SessionUserContext.getSessionUser())
                .with("ip", IpUtil.getRealUserIp(request))
                .with("referer", request.getHeader(SessionConstants.REFERER_HEADER))
                .with("ua", request.getHeader(SessionConstants.USER_AGENT_HEADER))
                .info();
    }
}

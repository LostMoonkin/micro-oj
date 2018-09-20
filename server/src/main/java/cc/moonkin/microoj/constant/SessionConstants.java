package cc.moonkin.microoj.constant;

/**
 * @author anchun
 *
 */
public interface SessionConstants {
    String TOKEN_HEADER = "Authorization";

    String TOKEN_PREFIX = "bearer";

    String START_TIME_ATTRIBUTE = "start-time";

    String IP_HEADER = "X-Forwarded-For";

    String REFERER_HEADER = "Referer";

    String USER_AGENT_HEADER = "user-agent";

    String PROXY_IP_HEADER = "Proxy-Client-IP";

    String WL_PROXY_IP_HEADER = "WL-Proxy-Client-IP";

    String REAL_IP_HEADER = "X-Real-IP";
}

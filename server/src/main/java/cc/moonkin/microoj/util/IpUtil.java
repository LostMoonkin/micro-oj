package cc.moonkin.microoj.util;

import cc.moonkin.microoj.constant.SessionConstants;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;

/**
 * @author anchun
 *
 */
public class IpUtil {

    private static final String UNKNOWN = "unknown";

    public static String getRealUserIp(@NotNull final HttpServletRequest request) {
        String ip = request.getHeader(SessionConstants.IP_HEADER);
        if(isUnknownIp(ip)){
            ip=request.getHeader(SessionConstants.PROXY_IP_HEADER);
        }
        if(isUnknownIp(ip)){
            ip=request.getHeader(SessionConstants.WL_PROXY_IP_HEADER);
        }
        if(isUnknownIp(ip)){
            ip=request.getHeader(SessionConstants.REAL_IP_HEADER);
        }
        if(isUnknownIp(ip)){
            ip=request.getRemoteAddr();
        }
        return resolveIP(ip);
    }

    private static String resolveIP(final String ip) {
        if (!isUnknownIp(ip)) {
            final String[] ips = ip.split(",");
            for (int i = ips.length - 1; i >= 0; i--) {
                if (!UNKNOWN.equalsIgnoreCase(ips[i]) && isValidIpFormat(ips[i])) {
                    return ips[i].trim();
                }
            }
        }
        return null;
    }

    private static boolean isValidIpFormat(final String input) {
        if (StringUtils.isEmpty(input)) {
            return false;
        }
        final String[] parts = input.trim().split("\\.");
        if (parts.length != 4) {
            return false;
        }
        for (final String part : parts) {
            if (!part.matches("\\d+")) {
                return false;
            }
            final int number = Integer.valueOf(part);
            if (number < 0 || number > 255) {
                return false;
            }
        }
        return true;
    }

    private static boolean isUnknownIp(final String ip) {
        return StringUtils.isEmpty(ip) || UNKNOWN.equalsIgnoreCase(ip);
    }
}

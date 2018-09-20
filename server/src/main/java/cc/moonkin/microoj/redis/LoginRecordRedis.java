package cc.moonkin.microoj.redis;

import cc.moonkin.microoj.data.SessionUser;
import cc.moonkin.microoj.log.MicroLog;
import cc.moonkin.microoj.util.SessionUserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author anchun
 *
 */
@Component
public class LoginRecordRedis {

    private static final MicroLog LOG = MicroLog.getLogger(LoginRecordRedis.class);

    private static final String KEY_PREFIX = "login-record:";

    private static final String COUNT_KEY_PREFIX = KEY_PREFIX + "count:";

    private static final String FORBIDDEN_KEY_PREFIX = KEY_PREFIX + "forbidden:";

    @Autowired
    private ValueOperations operations;

    @Value("${micro-oj.login.max-attempts:5}")
    private Long maxAttempts;

    @Value("${micro-oj.login.duration:3600}")
    private Long duration;

    @Value("${micro-oj.login.forbidden-time:7200}")
    private Long forbiddenTime;

    @SuppressWarnings("unchecked")
    public boolean validUser(final Long userId) {

        final String banKey = FORBIDDEN_KEY_PREFIX + userId;

        final SessionUser sessionUser = (SessionUser) operations.get(banKey);
        if (sessionUser != null) {
            LOG.message("validUser", "user was already banned.")
                    .with("user", sessionUser)
                    .warn();
            return false;
        }

        final String countKey = COUNT_KEY_PREFIX + userId;

        Integer count = (Integer) operations.get(countKey);
        if (count != null) {
            count = count + 1;
        } else {
            count = 1;
        }
        operations.set(countKey, count, duration, TimeUnit.SECONDS);
        if (count > maxAttempts) {
            LOG.message("validUser", "ban user")
                    .with("userId", userId)
                    .warn();
            SessionUser forbiddenUser = SessionUserContext.getSessionUser();
            if (forbiddenUser != null && !userId.equals(forbiddenUser.getId())) {
                forbiddenUser = new SessionUser();
                forbiddenUser.setId(userId);
            }
            operations.set(banKey, forbiddenUser, forbiddenTime, TimeUnit.SECONDS);
            return false;
        }
        return true;
    }
}

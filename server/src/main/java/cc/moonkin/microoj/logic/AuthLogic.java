package cc.moonkin.microoj.logic;

import cc.moonkin.microoj.constant.SessionConstants;
import cc.moonkin.microoj.data.LoginRecord;
import cc.moonkin.microoj.data.User;
import cc.moonkin.microoj.db.LoginRecordDb;
import cc.moonkin.microoj.db.UserDb;
import cc.moonkin.microoj.exception.LogInFailException;
import cc.moonkin.microoj.exception.UserForbiddenException;
import cc.moonkin.microoj.log.MicroLog;
import cc.moonkin.microoj.redis.LoginRecordRedis;
import cc.moonkin.microoj.util.IpUtil;
import cc.moonkin.microoj.util.JWTHelper;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * @author anchun
 *
 */
@Service
public class AuthLogic {

    private static final MicroLog LOG = MicroLog.getLogger(AuthLogic.class);

    @Autowired
    private JWTHelper jwtHelper;

    @Autowired
    private UserDb userDb;

    @Autowired
    private LoginRecordRedis recordRedis;

    @Autowired
    private LoginRecordDb recordDb;

    public boolean createUser(final User user) {
        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
        return userDb.addUser(user);
    }

    public String logIn(final HttpServletRequest request, final String userName, final String email,
            final String phone, final String password, final boolean remember)
            throws LogInFailException, UserForbiddenException {
        final User user = userDb.getLoginUser(userName, email, phone);
        if (user == null) {
            throw new LogInFailException("User not found");
        }
        final int status = BCrypt.checkpw(password, user.getPassword()) ? 1 : 0;
        if (!recordDb.addRecord(new LoginRecord(user.getId(), IpUtil.getRealUserIp(request),
                status, request.getHeader(SessionConstants.USER_AGENT_HEADER), null))) {
            LOG.message("logIn", "add login-record fail")
                    .with("user", user)
                    .warn();
        }
        if (status == 0) {
            if (!recordRedis.validUser(user.getId())) {
                throw new UserForbiddenException();
            }
            throw new LogInFailException("Wrong password");
        }
        return remember ? jwtHelper.createLongToken(user.getId(), user.getRole()) :
                jwtHelper.createDefaultToken(user.getId(), user.getRole());
    }

}

package cc.moonkin.microoj.logic;

import cc.moonkin.microoj.data.User;
import cc.moonkin.microoj.db.UserDb;
import cc.moonkin.microoj.exception.LogInFailException;
import cc.moonkin.microoj.log.MicroLog;
import cc.moonkin.microoj.util.JWTHelper;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author anchun
 *
 */
@Component
public class AuthLogic {

    private static final MicroLog LOG = MicroLog.getLogger(AuthLogic.class);

    @Autowired
    private JWTHelper jwtHelper;

    @Autowired
    private UserDb userDb;

    public boolean createUser(final User user) {
        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
        return userDb.addUser(user);
    }

    public String logIn(final String userName, final String email, final String phone,
            final String password, final boolean remember) throws LogInFailException {
        final User user = userDb.getLoginUser(userName, email, phone);
        if (user == null) {
            throw new LogInFailException("User not found");
        }
        if (!BCrypt.checkpw(password, user.getPassword())) {
            throw new LogInFailException("Wrong password");
        }
        return remember ? jwtHelper.createLongToken(user.getId(), user.getRole()) :
                jwtHelper.createDefaultToken(user.getId(), user.getRole());
    }

}

package cc.moonkin.microoj.logic;

import cc.moonkin.microoj.data.User;
import cc.moonkin.microoj.db.UserDb;
import cc.moonkin.microoj.log.MicroLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author anchun
 *
 */
@Service
public class UserLogic {

    private static final MicroLog LOG = MicroLog.getLogger(UserLogic.class);

    @Autowired
    private UserDb userDb;

    public User getDisplayUserById(final Long id) {
        if (id == null) {
            LOG.message("getDisplayUserById", "userId is null")
                    .warn();
            return null;
        }
        return userDb.getDisplayUser(id, null, null, null);
    }

}

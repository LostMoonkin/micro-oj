package cc.moonkin.microoj.db;

import cc.moonkin.microoj.data.User;
import cc.moonkin.microoj.db.mapper.UserMapper;
import cc.moonkin.microoj.util.ParamUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author anchun
 *
 */
@Component
public class UserDb {

    @Autowired
    private UserMapper userMapper;

    public boolean addUser(final User user) {
        return userMapper.addUser(user);
    }

    public User getDisplayUser(final Long id, final String userName, final String email,
            final String phone) {
        return userMapper.getDisplayUser(id, userName, email, phone);
    }

    public User getLoginUser(final String userName, final String email, final String phone) {
        if (ParamUtil.allEmpty(userName, email, phone)) {
            return null;
        }
        return userMapper.getLoginUser(userName, email, phone);
    }
}

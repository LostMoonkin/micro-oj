package cc.moonkin.microoj.db.mapper;

import cc.moonkin.microoj.data.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * @author anchun
 *
 */
@Component
public interface UserMapper {

    boolean addUser(final User user);

    User getDisplayUser(@Param("id") final Long id, @Param("userName") final String userName,
            @Param("email") final String email, @Param("phone") final String phone);

    User getLoginUser(@Param("userName") final String userName, @Param("email") final String email,
            @Param("phone") final String phone);
}

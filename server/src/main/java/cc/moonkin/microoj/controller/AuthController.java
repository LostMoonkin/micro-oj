package cc.moonkin.microoj.controller;

import cc.moonkin.microoj.constant.api.AuthApi;
import cc.moonkin.microoj.constant.api.UserApi;
import cc.moonkin.microoj.data.User;
import cc.moonkin.microoj.data.enums.Role;
import cc.moonkin.microoj.exception.LogInFailException;
import cc.moonkin.microoj.log.MicroLog;
import cc.moonkin.microoj.logic.AuthLogic;
import cc.moonkin.microoj.util.ParamUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * @author anchun
 *
 */
@RestController
public class AuthController {

    private static final MicroLog LOG = MicroLog.getLogger(AuthController.class);

    @Autowired
    private AuthLogic authLogic;

    @PostMapping(value = AuthApi.SIGN_UP_API)
    public Boolean signUp(@RequestBody final User user, final HttpServletResponse response) {
        if (user == null || ParamUtil.allEmpty(user.getUserName(), user.getEmail(),
                user.getPassword(), user.getPhone())) {
            LOG.message("addUser", "has empty params")
                    .withPojo("user", user)
                    .warn();
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return null;
        }
        user.setRole(Role.MEMBER.value());
        return authLogic.createUser(user);
    }

    @PostMapping(value = AuthApi.LOGIN_API)
    public String logIn(
            @RequestParam(value = "username", required = false) final String userName,
            @RequestParam(value = "email", required = false) final String email,
            @RequestParam(value = "password") final String password,
            @RequestParam(value = "remember", defaultValue = "false", required = false) final boolean remember,
            final HttpServletResponse response) {
        if (ParamUtil.allEmpty(userName, email)) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return null;
        }
        try {
            final String token = authLogic.logIn(userName, email, null, password, remember);
            if (token == null) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                return null;
            }
            return token;
        } catch (final LogInFailException e) {
            LOG.message("getLoginUser", e)
                    .with("userName", userName)
                    .with("email", email)
                    .with("password", password)
                    .warn();
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return null;
        }
    }
}

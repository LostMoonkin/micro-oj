package cc.moonkin.microoj.controller;

import cc.moonkin.microoj.annotation.Auth;
import cc.moonkin.microoj.constant.api.UserApi;
import cc.moonkin.microoj.data.User;
import cc.moonkin.microoj.log.MicroLog;
import cc.moonkin.microoj.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author anchun
 *
 */
@RestController
public class UserController {

    private static final MicroLog LOG = MicroLog.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @GetMapping(value = UserApi.USER_DISPLAY_API)
    @Auth
    public User getDisplayUser(@RequestParam(value = "id") final Long id) {
        return userService.getDisplayUserById(id);
    }
}

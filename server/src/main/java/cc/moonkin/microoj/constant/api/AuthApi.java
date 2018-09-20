package cc.moonkin.microoj.constant.api;

import cc.moonkin.microoj.constant.ApiPathConstants;

/**
 * @author anchun
 *
 */
public interface AuthApi {

    String AUTH_API = ApiPathConstants.REST_API_PREFIX + "/auth";

    String LOGIN_API = AUTH_API + "/login";

    String SIGN_UP_API = AUTH_API + "/signup";
}

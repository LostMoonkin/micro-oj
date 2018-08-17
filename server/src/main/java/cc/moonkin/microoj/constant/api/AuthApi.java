package cc.moonkin.microoj.constant.api;

import cc.moonkin.microoj.constant.ApiPathConstants;

/**
 * @author anchun
 *
 */
public class AuthApi {

    public static final String AUTH_API = ApiPathConstants.REST_API_PREFIX + "/auth";

    public static final String LOGIN_API = AUTH_API + "/login";

    public static final String SIGN_UP_API = AUTH_API + "/signup";
}

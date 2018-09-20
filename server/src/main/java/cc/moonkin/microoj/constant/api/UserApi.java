package cc.moonkin.microoj.constant.api;

import cc.moonkin.microoj.constant.ApiPathConstants;

/**
 * @author anchun
 *
 */
public interface UserApi {

    String USER_API = ApiPathConstants.REST_API_PREFIX + "/user";

    String USER_DISPLAY_API = USER_API + "/display";
}

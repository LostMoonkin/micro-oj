package cc.moonkin.microoj.constant.api;

import cc.moonkin.microoj.constant.ApiPathConstants;

/**
 * @author anchun
 *
 */
public interface RecordApi {

    String RECORD_API = ApiPathConstants.REST_API_PREFIX + "/record";

    String USER_ID_API = RECORD_API + "/user";

    String IP_API = RECORD_API + "/ip";
}

/*
 * ParamUtil.java
 * Copyright 2018 Qunhe Tech, all rights reserved.
 * Qunhe PROPRIETARY/CONFIDENTIAL, any form of usage is subject to approval.
 */

package cc.moonkin.microoj.util;

import org.springframework.util.StringUtils;

/**
 * @author anchun
 *
 */
public class ParamUtil {

    public static boolean allEmpty(final String... params) {
        for (final String param : params) {
            if (!StringUtils.isEmpty(param)) {
                return false;
            }
        }
        return true;
    }
}

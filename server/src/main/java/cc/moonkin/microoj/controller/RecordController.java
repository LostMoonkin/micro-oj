package cc.moonkin.microoj.controller;

import cc.moonkin.microoj.advice.Auth;
import cc.moonkin.microoj.constant.api.RecordApi;
import cc.moonkin.microoj.data.LoginRecord;
import cc.moonkin.microoj.data.enums.Role;
import cc.moonkin.microoj.log.MicroLog;
import cc.moonkin.microoj.logic.LoginRecordLogic;
import cc.moonkin.microoj.util.LongCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author anchun
 *
 */
@RestController
public class RecordController {

    private static final MicroLog LOG = MicroLog.getLogger(RecordController.class);

    @Autowired
    private LoginRecordLogic recordLogic;

    @Autowired
    private LongCrypt longCrypt;

    @GetMapping(RecordApi.RECORD_API)
    @Auth(role = Role.SYSTEM_ADMIN)
    public List<LoginRecord> getLatestRecord(
            @RequestParam(value = "status", required = false) final Integer status,
            @RequestParam(value = "start") final Long start,
            @RequestParam(value = "end") final Long end,
            @RequestParam(value = "page", defaultValue = "0") final int page,
            @RequestParam(value = "limit", defaultValue = "10") final int limit) {
        return recordLogic.getLatestRecord(new Timestamp(start), new Timestamp(end), status, page
                , limit);
    }

    @GetMapping(RecordApi.USER_ID_API)
    @Auth(role = Role.SYSTEM_ADMIN)
    public List<LoginRecord> getLoginRecordByUserId(
            @RequestParam(value = "obsid") final String obsUserId,
            @RequestParam(value = "status", required = false) final Integer status,
            @RequestParam(value = "start") final Long start,
            @RequestParam(value = "end") final Long end,
            @RequestParam(value = "page", defaultValue = "0") final int page,
            @RequestParam(value = "limit", defaultValue = "10") final int limit) {
        final Long userId = longCrypt.decrypt(obsUserId);
        return recordLogic.getLoginRecordByUserId(userId, status, new Timestamp(start),
                new Timestamp(end), page, limit);
    }

    @GetMapping(RecordApi.IP_API)
    @Auth(role = Role.SYSTEM_ADMIN)
    public List<LoginRecord> getLoginRecordByIp(
            @RequestParam(value = "ip") final String ip,
            @RequestParam(value = "status", required = false) final Integer status,
            @RequestParam(value = "start") final Long start,
            @RequestParam(value = "end") final Long end,
            @RequestParam(value = "page", defaultValue = "0") final int page,
            @RequestParam(value = "limit", defaultValue = "10") final int limit) {
        return recordLogic.getLoginRecordByIp(ip, status, new Timestamp(start),
                new Timestamp(end), page, limit);
    }
}

package cc.moonkin.microoj.service;

import cc.moonkin.microoj.data.LoginRecord;
import cc.moonkin.microoj.db.LoginRecordDb;
import cc.moonkin.microoj.log.MicroLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author anchun
 *
 */
@Service
public class LoginRecordService {

    private static final MicroLog LOG = MicroLog.getLogger(LoginRecordService.class);

    @Autowired
    private LoginRecordDb recordDb;

    public List<LoginRecord> getLatestRecord(final Timestamp start, final Timestamp end,
            final Integer status, final int page, final int limit) {
        final int colNum = page * limit;
        if (start == null || end == null) {
            LOG.message("getLoginRecordByUserId", "time is null")
                    .warn();
            return null;
        }
        return recordDb.getRecords(start, end, status, colNum, limit);
    }

    public List<LoginRecord> getLoginRecordByUserId(final Long userId, final Integer status,
            final Timestamp start, final Timestamp end, final int page, final int limit) {
        final int colNum = page * limit;
        if (userId == null || start == null || end == null) {
            LOG.message("getLoginRecordByUserId", "userId and time is null")
                    .warn();
            return null;
        }
        return recordDb.getRecordByUserId(userId, status, start, end, colNum, limit);
    }

    public List<LoginRecord> getLoginRecordByIp(final String ip, final Integer status,
            final Timestamp start, final Timestamp end, final int page, final int limit) {
        final int colNum = page * limit;
        if (ip == null || start == null || end == null) {
            LOG.message("getLoginRecordByIp", "ip and time is null")
                    .warn();
            return null;
        }
        return recordDb.getRecordByIp(ip, status, start, end, colNum, limit);
    }
}

package cc.moonkin.microoj.db;

import cc.moonkin.microoj.data.LoginRecord;
import cc.moonkin.microoj.db.mapper.LoginRecordMapper;
import cc.moonkin.microoj.log.MicroLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author anchun
 *
 */
@Component
public class LoginRecordDb {

    private static final MicroLog LOG = MicroLog.getLogger(LoginRecord.class);

    @Autowired
    private LoginRecordMapper recordMapper;

    public boolean addRecord(final LoginRecord record) {
        if (record.getUserId() == null) {
            LOG.message("addRecord", "userId is null")
                    .with("record", record)
                    .warn();
            return false;
        }
        return recordMapper.addRecord(record);
    }

    public List<LoginRecord> getRecords(final Timestamp start, final Timestamp end,
            final Integer status, final int colNum, final int limit) {
        return recordMapper.multiGet(status, start, end, colNum, limit);
    }

    public List<LoginRecord> getRecordByUserId(final Long userId, final Integer status,
            final Timestamp start, final Timestamp end, final int colNum, final int limit) {
        return recordMapper.getByUserId(userId, status, start, end, colNum, limit);
    }

    public List<LoginRecord> getRecordByIp(final String ip, final Integer status,
            final Timestamp start, final Timestamp end, final int colNum, final int limit) {
        return recordMapper.getByIp(ip, status, start, end, colNum, limit);
    }

}

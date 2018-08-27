package cc.moonkin.microoj.db.mapper;

import cc.moonkin.microoj.data.LoginRecord;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author anchun
 *
 */
@Component
public interface LoginRecordMapper {

    boolean addRecord(final LoginRecord record);

    List<LoginRecord> multiGet(
            @Param("status") final Integer status,
            @Param("start") final Timestamp start,
            @Param("end") final Timestamp end,
            @Param("colNum") final int colNum,
            @Param("limit") final int limit);

    List<LoginRecord> getByUserId(
            @Param("userId") final Long userId,
            @Param("status") final Integer status,
            @Param("start") final Timestamp start,
            @Param("end") final Timestamp end,
            @Param("colNum") final int colNum,
            @Param("limit") final int limit);

    List<LoginRecord> getByIp(
            @Param("ip") final String ip,
            @Param("status") final Integer status,
            @Param("start") final Timestamp start,
            @Param("end") final Timestamp end,
            @Param("colNum") final int colNum,
            @Param("limit") final int limit);
}

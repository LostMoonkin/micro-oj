package cc.moonkin.microoj.db.mapper;

import cc.moonkin.microoj.data.Problem;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author anchun
 *
 */
@Component
public interface ProblemMapper {

    boolean add(final Problem problem);

    boolean update(final Problem problem);

    boolean delete(@Param("id") final long id);

    boolean recover(@Param("id") final long id);

    Problem get(@Param("id") final long id, @Param("deleted") final boolean deleted);

    List<Problem> multiGet(@Param("start") final int start, @Param("limit") final int limit,
            @Param("deleted") final boolean deleted);

    Long count(@Param("deleted") final boolean deleted);
}

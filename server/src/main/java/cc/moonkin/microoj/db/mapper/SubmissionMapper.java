package cc.moonkin.microoj.db.mapper;

import cc.moonkin.microoj.data.Submission;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author anchun
 *
 */
@Component
public interface SubmissionMapper {

    boolean add(final Submission submission);

    boolean updateStatus(@Param("id") final long id, @Param("status") final Integer status);

    Submission get(@Param("id") final long id);

    List<Submission> multiGetDesc(@Param("start")final int start, @Param("limit") final int limit);

    List<Submission> getByUser(@Param("userId") final long userId, @Param("start") final int start,
            @Param("limit") final int limit);

    List<Submission> getByProblem(@Param("problemId") final long problemId,
            @Param("start") final int start, @Param("limit") final int limit);

}

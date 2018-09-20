package cc.moonkin.microoj.db.mapper;

import cc.moonkin.microoj.data.TestCase;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author anchun
 *
 */
@Component
public interface TestCaseMapper {

    int multiAdd(final List<TestCase> testCases);

    boolean update(final TestCase testCase);

    boolean delete(@Param("id") final long id);

    boolean recover(@Param("id") final long id);

    TestCase get(@Param("id") final long id, @Param("deleted") final boolean deleted);

    List<TestCase> getByProblemId(@Param("problemId") final long problemId,
            @Param("deleted") final boolean deleted);
}

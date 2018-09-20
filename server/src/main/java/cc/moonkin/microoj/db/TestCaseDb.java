package cc.moonkin.microoj.db;

import cc.moonkin.microoj.data.TestCase;
import cc.moonkin.microoj.db.mapper.TestCaseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author anchun
 *
 */
@Component
public class TestCaseDb {

    @Autowired
    private TestCaseMapper testCaseMapper;

    public boolean addTestCases(final List<TestCase> testCases) {
        return testCaseMapper.multiAdd(testCases) > 0;
    }
}

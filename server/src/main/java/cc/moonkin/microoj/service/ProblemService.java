package cc.moonkin.microoj.service;

import cc.moonkin.microoj.data.Problem;
import cc.moonkin.microoj.data.TestCase;
import cc.moonkin.microoj.db.ProblemDb;
import cc.moonkin.microoj.db.TestCaseDb;
import cc.moonkin.microoj.exception.ServerException;
import cc.moonkin.microoj.log.MicroLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author anchun
 *
 */
@Service
public class ProblemService {

    private static final MicroLog LOG = MicroLog.getLogger(ProblemService.class);

    @Autowired
    private ProblemDb problemDb;

    @Autowired
    private TestCaseDb testCaseDb;

    public boolean addProblem(final Problem problem, final List<TestCase> testCases) {
        for (final TestCase testCase : testCases) {
            if (!testCase.getProblemId().equals(problem.getId())) {
                throw new ServerException(0, "TestCase must has same id with problem");
            }
        }
        if (problem.isSpj()) {
            return problemDb.addProblem(problem);
        }
        return problemDb.addProblem(problem) && testCaseDb.addTestCases(testCases);
    }

}

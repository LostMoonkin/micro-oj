package cc.moonkin.microoj.db;

import cc.moonkin.microoj.constant.CacheConstants;
import cc.moonkin.microoj.data.Problem;
import cc.moonkin.microoj.db.mapper.ProblemMapper;
import cc.moonkin.microoj.log.MicroLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author anchun
 *
 */
@Component
public class ProblemDb {

    private static final MicroLog LOG = MicroLog.getLogger(ProblemDb.class);

    @Autowired
    private ProblemMapper problemMapper;

    public Problem getProblem(final Long problemId, final boolean includeDeleted) {
        if (problemId == null) {
            LOG.message("getProblem", "id is null")
                    .warn();
            return null;
        }
        return problemMapper.get(problemId, includeDeleted);
    }

    public List<Problem> multiGet(final int start, final int limit, final boolean includeDeleted) {
        return problemMapper.multiGet(start, limit, includeDeleted);
    }

    @Cacheable(value = CacheConstants.PROBLEM_COUNT_CACHE, key = "#root.methodName+':'+#p0")
    public Long countProblem(final Boolean includeDeleted) {
        return problemMapper.count(includeDeleted);
    }

    @CacheEvict(value = CacheConstants.PROBLEM_COUNT_CACHE, allEntries = true)
    public boolean addProblem(final Problem problem) {
        if (problem == null) {
            LOG.message("addProblem", "problem is null")
                    .warn();
            return false;
        }
        return problemMapper.add(problem);
    }

    public boolean deleteProblem(final Long problemId) {
        if (problemId == null) {
            LOG.message("deleteProblem", "id is null")
                    .warn();
            return false;
        }
        return problemMapper.delete(problemId);
    }

    public boolean recoverProblem(final Long problemId) {
        if (problemId == null) {
            LOG.message("recoverProblem", "id is null")
                    .warn();
            return false;
        }
        return problemMapper.recover(problemId);
    }
}

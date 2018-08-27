package cc.moonkin.microoj.db;

import cc.moonkin.microoj.constant.CacheConstants;
import cc.moonkin.microoj.data.Problem;
import cc.moonkin.microoj.db.mapper.ProblemMapper;
import cc.moonkin.microoj.log.MicroLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

/**
 * @author anchun
 *
 */
@Component
public class ProblemDb {

    private static final MicroLog LOG = MicroLog.getLogger(ProblemDb.class);

    @Autowired
    private ProblemMapper problemMapper;

    @Cacheable(value = CacheConstants.PROBLEM_COUNT_CACHE, key = "#root.methodName+':'+#p0")
    public Long countProblem(final Boolean includeDeleted) {
        return problemMapper.count(includeDeleted);
    }

    public boolean addProblem(final Problem problem) {
        if (problem == null) {
            LOG.message("addProblem", "problem is null")
                    .warn();
            return false;
        }
        return problemMapper.add(problem);
    }
}

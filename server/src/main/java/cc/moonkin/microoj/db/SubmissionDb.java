package cc.moonkin.microoj.db;

import cc.moonkin.microoj.data.Submission;
import cc.moonkin.microoj.data.enums.SubmissionStatus;
import cc.moonkin.microoj.db.mapper.SubmissionMapper;
import cc.moonkin.microoj.log.MicroLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * @author anchun
 *
 */
@Component
public class SubmissionDb {

    private static final MicroLog LOG = MicroLog.getLogger(SubmissionDb.class);

    @Autowired
    private SubmissionMapper submissionMapper;

    public boolean addSubmission(final Submission submission) {
        if (submission == null) {
            LOG.message("addSubmission", "submission is null")
                    .warn();
            return false;
        }
        return submissionMapper.add(submission);
    }

    public boolean updateStatus(final Long id, final SubmissionStatus status) {
        if (id == null || status == null) {
            LOG.message("updateStatus", "id or status is null")
                    .warn();
            return false;
        }
        return submissionMapper.updateStatus(id, status.value());
    }

    public Submission getSubmission(final Long id) {
        if (id == null) {
            LOG.message("getSubmission", "id is null")
                    .warn();
            return null;
        }
        return submissionMapper.get(id);
    }

    public List<Submission> multiGetDesc(final int start, final int limit) {
        return submissionMapper.multiGetDesc(start, limit);
    }

    public List<Submission> getByProblem(final Long problemId, final int start, final int limit) {
        if (problemId == null) {
            LOG.message("getByProblem", "problemId is null")
                    .warn();
            return Collections.emptyList();
        }
        return submissionMapper.getByProblem(problemId, start, limit);
    }

    public List<Submission> getByUser(final Long userId, final int start, final int limit) {
        if (userId == null) {
            LOG.message("getByUser", "userId is null")
                    .warn();
            return Collections.emptyList();
        }
        return submissionMapper.getByUser(userId, start, limit);
    }
}

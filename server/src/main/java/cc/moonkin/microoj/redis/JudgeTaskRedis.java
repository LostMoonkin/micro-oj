package cc.moonkin.microoj.redis;

import cc.moonkin.microoj.log.MicroLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.stereotype.Component;

/**
 * @author anchun
 *
 */
@Component
public class JudgeTaskRedis {

    private static final MicroLog LOG = MicroLog.getLogger(JudgeTaskRedis.class);

    private static final String KEY_PREFIX = "judge-task:";

    private static final String TASK_QUEUE_KEY = KEY_PREFIX + "queue";

    @Autowired
    private ListOperations listOperations;

    @SuppressWarnings("unchecked")
    public boolean addTask(final Long taskId) {
        if (taskId == null) {
            LOG.message("addTask - task is null")
                    .warn();
            return false;
        }
        final Long result = listOperations.leftPush(TASK_QUEUE_KEY, taskId);
        return result != null && result > 0;
    }
}

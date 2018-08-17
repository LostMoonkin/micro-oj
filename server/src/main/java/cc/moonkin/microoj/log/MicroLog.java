package cc.moonkin.microoj.log;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author anchun
 *
 */
@Getter
public final class MicroLog {

    private static final String SEPARATE = " - ";

    private final Logger logger;

    private final boolean traceEnable;

    private final boolean debugEnable;

    private final boolean infoEnable;

    private final boolean warnEnable;

    private final boolean errorEnable;

    public static MicroLog getLogger(final Class c) {
        return new MicroLog(c);
    }

    private MicroLog(final Class c) {
        logger = LoggerFactory.getLogger(c);
        traceEnable = logger.isTraceEnabled();
        debugEnable = logger.isDebugEnabled();
        infoEnable = logger.isInfoEnabled();
        warnEnable = logger.isWarnEnabled();
        errorEnable = logger.isErrorEnabled();
    }

    public LogBase message(final String message) {
        return new LogBase(message, this);
    }

    public LogBase message(final String message, final Throwable throwable) {
        return new LogBase(message, this, throwable);
    }

    public LogBase message(final String function, final String description) {
        return message(function + SEPARATE + description);
    }

    public LogBase message(final String function, final String description,
            final Throwable throwable) {
        return message(function + SEPARATE + description, throwable);
    }

}
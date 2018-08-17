package cc.moonkin.microoj.log;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author anchun
 *
 */
public class LogBase {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private final List<LogTuple> tuples = new LinkedList<>();

    private Throwable throwable;

    private final String message;

    private final MicroLog log;

    public LogBase(final String message, final MicroLog log) {
        this(message, log, null);
    }

    public LogBase(final String message, final MicroLog log, final Throwable throwable) {
        this.message = message;
        this.log = log;
        this.throwable = throwable;
    }

    /**
     * add key and value to log.
     * @param prefix
     * @param value
     * @return
     */
    public LogBase with(final String prefix, final Object value) {
        if (prefix == null) {
            return this;
        }
        if (value instanceof Throwable && throwable == null) {
            throwable = (Throwable) value;
        }
        tuples.add(new LogTuple(prefix, value));
        return this;
    }

    /**
     * add a map into log.
     * @param map
     * @param prefix
     * @return
     */
    public LogBase with(final Map<String, Object> map, final String prefix) {
        if (map == null) {
            return this;
        }
        final String realPrefix = prefix == null || prefix.isEmpty() ? "" : prefix;
        map.forEach((key, value) -> with(realPrefix + key, value));
        return this;
    }

    /**
     * add key and value to
     * @param prefix
     * @param value
     * @return
     */
    public LogBase withPojo(final String prefix, final Object value) {
        final Map<String, Object> map = MAPPER.convertValue(value, new TypeReference<Map<String,
                Object>>() {});
        return with(map, prefix);
    }

    public void trace() {
        if (log != null) {
            if (log.isTraceEnable()) {
                if (throwable != null) {
                    log.getLogger().trace(getLogString(), throwable);
                } else {
                    log.getLogger().trace(getLogString());
                }
            }
        }
        tuples.clear();
    }

    public void debug() {
        if (log != null) {
            if (log.isDebugEnable()) {
                if (throwable != null) {
                    log.getLogger().debug(getLogString(), throwable);
                } else {
                    log.getLogger().debug(getLogString());
                }
            }
        }
        tuples.clear();
    }

    public void info() {
        if (log != null) {
            if (log.isInfoEnable()) {
                if (throwable != null) {
                    log.getLogger().info(getLogString(), throwable);
                } else {
                    log.getLogger().info(getLogString());
                }
            }
        }
        tuples.clear();
    }

    public void warn() {
        if (log != null) {
            if (log.isWarnEnable()) {
                if (throwable != null) {
                    log.getLogger().warn(getLogString(), throwable);
                } else {
                    log.getLogger().warn(getLogString());
                }
            }
        }
        tuples.clear();
    }

    public void error() {
        if (log != null) {
            if (log.isErrorEnable()) {
                if (throwable != null) {
                    log.getLogger().error(getLogString(), throwable);
                } else {
                    log.getLogger().error(getLogString());
                }
            }
        }
        tuples.clear();
    }

    private String getLogString() {
        final StringBuilder builder = new StringBuilder(message);
        if (tuples.isEmpty()) {
            return builder.toString();
        }
        builder.append(LogConstants.COLON);
        tuples.forEach(builder::append);
        return builder.toString();
    }

}

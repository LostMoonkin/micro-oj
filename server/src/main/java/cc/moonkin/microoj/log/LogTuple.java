package cc.moonkin.microoj.log;

import lombok.Getter;
import lombok.Setter;

/**
 * @author anchun
 *
 */
@Getter
@Setter
public class LogTuple {

    private String prefix;

    private Object value;

    public LogTuple() {

    }

    public LogTuple(final String prefix, final Object value) {
        this.prefix = prefix;
        this.value = value;
    }

    @Override
    public String toString() {
        return prefix + LogConstants.COLON + value + LogConstants.SEPARATE;
    }
}

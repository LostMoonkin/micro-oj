package cc.moonkin.microoj.exception;

/**
 * @author anchun
 *
 */
public class LogInFailException extends Exception {

    public LogInFailException(final String message) {
        super(message);
    }

    public LogInFailException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public LogInFailException(final Throwable cause) {
        super(cause);
    }
}

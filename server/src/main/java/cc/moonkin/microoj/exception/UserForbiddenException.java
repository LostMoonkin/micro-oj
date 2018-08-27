package cc.moonkin.microoj.exception;

/**
 * @author anchun
 *
 */
public class UserForbiddenException extends Exception {

    public UserForbiddenException() {
    }

    public UserForbiddenException(final String message) {
        super(message);
    }

    public UserForbiddenException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public UserForbiddenException(final Throwable cause) {
        super(cause);
    }
}

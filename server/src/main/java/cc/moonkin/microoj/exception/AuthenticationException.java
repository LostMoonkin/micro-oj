package cc.moonkin.microoj.exception;

/**
 * @author anchun
 *
 */
public class AuthenticationException extends RuntimeException {

    public AuthenticationException() {
    }

    public AuthenticationException(final String message) {
        super(message);
    }
}

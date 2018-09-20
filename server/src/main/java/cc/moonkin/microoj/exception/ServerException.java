package cc.moonkin.microoj.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * @author anchun
 *
 */
@Getter
@Setter
public class ServerException extends RuntimeException {

    private int code;

    private String status;

    public ServerException(final int code, final String status) {
        super(String.format("code: %d, status: %s", code, status));
        this.code = code;
        this.status = status;
    }
}

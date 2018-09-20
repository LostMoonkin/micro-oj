package cc.moonkin.microoj.advice;

import cc.moonkin.microoj.data.response.GlobalResponse;
import cc.moonkin.microoj.exception.ServerException;
import cc.moonkin.microoj.log.MicroLog;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author anchun
 *
 */
@RestControllerAdvice
public class GlobalControllerAdvice {

    private static final MicroLog LOG = MicroLog.getLogger(GlobalControllerAdvice.class);

    @ExceptionHandler(value = ServerException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public GlobalResponse parseError(final ServerException e) {
        LOG.message("parseError")
                .with("code", e.getCode())
                .with("status", e.getStatus())
                .warn();
        return new GlobalResponse(e.getCode(), e.getStatus());
    }
}

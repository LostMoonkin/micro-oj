package cc.moonkin.microoj.data.response;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author anchun
 *
 */
@Data
@AllArgsConstructor
public class GlobalResponse {

    private int code;

    private String message;
}

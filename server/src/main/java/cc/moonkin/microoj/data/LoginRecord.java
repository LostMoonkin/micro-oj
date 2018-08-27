package cc.moonkin.microoj.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * @author anchun
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRecord {

    private Long userId;

    private String ip;

    private int status;

    private String userAgent;

    private Timestamp created;
}

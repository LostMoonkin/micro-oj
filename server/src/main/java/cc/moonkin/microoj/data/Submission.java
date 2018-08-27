package cc.moonkin.microoj.data;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * @author anchun
 *
 */
@Data
@NoArgsConstructor
public class Submission {

    private Long id;

    private Long problemId;

    private Long userId;

    private Integer status;

    private String code;

    private Integer language;

    private Timestamp created;

    private Timestamp lastModified;
}

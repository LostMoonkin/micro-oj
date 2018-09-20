package cc.moonkin.microoj.data;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * @author anchun
 *
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Problem {

    private Long id;

    private String title;

    private String content;

    private String sampleInput;

    private String sampleOutput;

    private String source;

    private Integer timeLimit;

    private Integer memoryLimit;

    private Integer stackLimit;

    private boolean spj;
}

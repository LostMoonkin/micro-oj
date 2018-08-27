package cc.moonkin.microoj.data;

import lombok.Data;

/**
 * @author anchun
 *
 */
@Data
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
}

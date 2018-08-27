package cc.moonkin.microoj.data.enums;

/**
 * @author anchun
 *
 */
public enum SubmissionStatus {

    SUCCESS(0),
    PENDING(1),
    COMPILING(2),
    JUDGING(3),
    COMPILE_ERROR(11),
    RUNTIME_ERROR(12),
    PRESENTATION_ERROR(13),
    CPU_TIME_OUT(14),
    REAL_TIME_OUT(15),
    OUT_OF_MEMORY(16);

    private final int code;

    SubmissionStatus(final int code) {
        this.code = code;
    }

    public static SubmissionStatus valueOf(final int status) {
        for (final SubmissionStatus submission : SubmissionStatus.values()) {
            if (status == submission.code) {
                return submission;
            }
        }
        return null;
    }

    public int value() {
        return this.code;
    }
}

package cc.moonkin.microoj.data.enums;

/**
 * @author anchun
 *
 */
public enum LoginStatus {

    FAILURE(0),
    SUCCESS(1);

    private final int code;

    LoginStatus(final int code) {
        this.code = code;
    }

    public static LoginStatus valueOf(final int role) {
        switch (role) {
        case 0:
            return FAILURE;
        case 1:
            return SUCCESS;
        default:
            return null;
        }
    }

    public int value() {
        return this.code;
    }
}

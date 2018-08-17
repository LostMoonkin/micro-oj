package cc.moonkin.microoj.data.enums;

/**
 * @author anchun
 *
 */

public enum Role {
    MEMBER(0),
    FORBIDDEN(-1),
    VIP(1),
    ADMIN(2),
    SYSTEM_ADMIN(3),
    ROOT(99);

    private final int code;

    Role(final int code) {
        this.code = code;
    }

    public static Role valueOf(final int role) {
        switch (role) {
        case 0:
            return MEMBER;
        case -1:
            return FORBIDDEN;
        case 1:
            return VIP;
        case 2:
            return ADMIN;
        case 3:
            return SYSTEM_ADMIN;
        case 99:
            return ROOT;
        default:
            return null;
        }
    }

    public int value() {
        return this.code;
    }

    public boolean lessThan(final Role other) {
        if (other == null) {
            return false;
        }
        return this.code < other.code;
    }
}

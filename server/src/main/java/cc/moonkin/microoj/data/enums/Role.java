package cc.moonkin.microoj.data.enums;

/**
 * @author anchun
 *
 */

public enum Role {
    MEMBER(0),
    FORBIDDEN(-1),
    VIP(1),
    GROUP_ADMIN(2),
    SYSTEM_ADMIN(3),
    ROOT(99);

    private final int code;

    Role(final int code) {
        this.code = code;
    }

    public static Role valueOf(final int role) {
        for (final Role cur : Role.values()) {
            if (role == cur.code) {
                return cur;
            }
        }
        return null;
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

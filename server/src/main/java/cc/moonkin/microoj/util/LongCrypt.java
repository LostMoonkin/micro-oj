package cc.moonkin.microoj.util;

import java.math.BigInteger;

/**
 * @author anchun
 *
 */
public class LongCrypt {

    private static final int COUNT = 4;

    private final long key;

    private final int radix;

    public LongCrypt(final long key, final int radix) {
        this.key = key;
        this.radix = radix;
    }

    public String encrypt(final Long id) {
        if (id == null) {
            return null;
        }
        long number = id;
        for (int i = 0; i < COUNT; i++) {
            number ^= key;
            number <<= 3;
        }
        return BigInteger.valueOf(number).toString(radix).toUpperCase();
    }

    public long decrypt(final String secret) {
        if (secret == null || secret.isEmpty()) {
            throw new IllegalArgumentException("decrypt - secret is empty");
        }
        try {
            final BigInteger number = new BigInteger(secret, radix);
            long result = number.longValue();
            for (int i = 0; i < COUNT; i++) {
                result >>= 3;
                result ^= key;
            }
            return result;
        } catch (final NumberFormatException e) {
            throw new IllegalArgumentException("decrypt - secret is invalid: " + secret);
        }
    }
}

package cc.moonkin.microoj.util;

import cc.moonkin.microoj.data.SessionUser;
import cc.moonkin.microoj.data.enums.Role;
import cc.moonkin.microoj.log.MicroLog;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author anchun
 *
 */
public class JWTHelper {

    private static final MicroLog LOG = MicroLog.getLogger(JWTHelper.class);

    private static final String ID_CLAIM = "id";

    private static final String ROLE_CLAIM = "role";

    private static final SignatureAlgorithm ALGORITHM = SignatureAlgorithm.HS256;

    private final Key signingKey;

    private final Long expire;

    private final Long longExpire;

    private final LongCrypt longCrypt;

    public JWTHelper(final String base64Key, final Long expire, final Long longExpire,
            final LongCrypt longCrypt) {
        final byte[] key = Base64.getDecoder().decode(base64Key);
        signingKey = new SecretKeySpec(key, ALGORITHM.getJcaName());
        this.expire = expire;
        this.longExpire = longExpire;
        this.longCrypt = longCrypt;
    }

    public String createDefaultToken(final Long id, final int role) {
        return createToken(id, role, expire);
    }

    public String createLongToken(final Long id, final int role) {
        return createToken(id, role, longExpire);
    }

    public SessionUser parserToken(final String token) {
        try {
            final Claims claims = Jwts.parser()
                    .setSigningKey(signingKey)
                    .parseClaimsJws(token)
                    .getBody();
            final String obsId = (String) claims.get(ID_CLAIM);
            final Role role = Role.valueOf((Integer) claims.get(ROLE_CLAIM));
            final Date expire = claims.getExpiration();
            final Long id = longCrypt.decrypt(obsId);
            return new SessionUser(id, obsId, role, expire);
        } catch (final Exception e) {
            LOG.message("parserToken", "parse fail.", e)
                    .with("token", token)
                    .warn();
            return null;
        }
    }

    private String createToken(final Long id, final int role, final Long expire) {
        if (id == null) {
            LOG.message("createToken", "id is null")
                    .warn();
            return null;
        }
        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .claim(ID_CLAIM, longCrypt.encrypt(id))
                .claim(ROLE_CLAIM, role)
                .setExpiration(new Date(System.currentTimeMillis() + expire))
                .setNotBefore(new Date())
                .signWith(ALGORITHM, signingKey)
                .compact();
    }

}

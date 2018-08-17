package cc.moonkin.microoj.config;

import cc.moonkin.microoj.util.JWTHelper;
import cc.moonkin.microoj.util.LongCrypt;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author anchun
 *
 */
@Configuration
@ConfigurationProperties(prefix = "jwt")
@Setter
public class JWTConfig {

    private String secret;

    private Long expiration;

    private Long longExpiration;

    @Bean
    @Autowired
    public JWTHelper jwtHelper(final LongCrypt longCrypt) {
        return new JWTHelper(secret, expiration, longExpiration, longCrypt);
    }

}

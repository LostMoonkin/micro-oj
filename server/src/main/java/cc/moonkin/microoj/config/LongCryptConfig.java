package cc.moonkin.microoj.config;

import cc.moonkin.microoj.util.LongCrypt;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author anchun
 *
 */

@Configuration
@ConfigurationProperties(prefix = "long-crypt")
@Setter
public class LongCryptConfig {

    private Long key;

    private Integer radix;

    @Bean
    public LongCrypt longCrypt() {
        return new LongCrypt(key, radix);
    }

}

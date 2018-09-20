package cc.moonkin.microoj.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author anchun
 *
 */
@Configuration
@ConfigurationProperties(prefix = "micro-oj")
@Setter
@Getter
public class MicroOJConfig {
}

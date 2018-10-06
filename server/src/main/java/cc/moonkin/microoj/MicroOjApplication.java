package cc.moonkin.microoj;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication(scanBasePackages = {
        "cc.moonkin.microoj"
})
@MapperScan("cc.moonkin.microoj.db.mapper")
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class MicroOjApplication {

    public static void main(final String[] args) {
        SpringApplication.run(MicroOjApplication.class, args);
    }
}

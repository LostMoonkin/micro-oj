package cc.moonkin.microoj;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
        "cc.moonkin.microoj"
})
@MapperScan("cc.moonkin.microoj.db.mapper")
public class MicroOjApplication {

    public static void main(final String[] args) {
        SpringApplication.run(MicroOjApplication.class, args);
    }
}

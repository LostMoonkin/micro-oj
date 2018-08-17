package cc.moonkin.microoj.config;

import cc.moonkin.microoj.interceptor.ApiLogInterceptor;
import cc.moonkin.microoj.interceptor.AuthInterceptor;
import cc.moonkin.microoj.interceptor.SessionUserInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author anchun
 *
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private SessionUserInterceptor sessionUserInterceptor;

    @Autowired
    private ApiLogInterceptor apiLogInterceptor;

    @Autowired
    private AuthInterceptor authInterceptor;

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(sessionUserInterceptor);
        registry.addInterceptor(apiLogInterceptor);
        registry.addInterceptor(authInterceptor);
    }
}

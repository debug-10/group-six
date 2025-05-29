package top.wxy.framework.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;


/**
 * @author 笼中雀
 */
@Configuration
public class PasswordConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        // 使用 PasswordEncoderFactories 创建默认的密码编码器
        // 这将支持 {bcrypt}、{noop}、{pbkdf2} 等前缀的密码格式
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
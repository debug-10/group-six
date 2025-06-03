package top.wxy.framework.common.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 笼中雀
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi userApi() {
        String[] paths = {"/**"};
        String[] packagedToMatch = {"top.wxy"};
        return GroupedOpenApi.builder().group("WxyCloud")
                .pathsToMatch(paths)
                .packagesToScan(packagedToMatch).build();
    }

    @Bean
    public OpenAPI customOpenAPI() {
        Contact contact = new Contact();
        contact.setName("3318547703@qq.com");

        return new OpenAPI().info(new Info()
                .title("wxyCloud")
                .description("WxyCloud")
                .contact(contact)
                .version("1.0")
                .termsOfService("https://wxy.top")
                .license(new License().name("MIT")
                        .url("https://wxy.top")));
    }

}
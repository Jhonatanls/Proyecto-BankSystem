package com.training.bank_system.config;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customizedOpenApi(){
        return new OpenAPI()
                .info(new Info()
                        .title("Bank System API")
                        .version("1.0")
                        .description("Documentación interactiva de los endpoints de la API"));
    }
}

package br.com.ingestionapi.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI nextLogisticaOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Ingestion API")
                        .description("API para recepção e validação de solicitações de otimização de rotas via IA.")
                        .version("v1.0")
                        .contact(new Contact()
                                .name("Leonardo Cantu")
                                .email("leonardo.cantu.dev@gmail.com")));
    }
}
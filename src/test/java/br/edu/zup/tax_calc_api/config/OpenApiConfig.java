package br.edu.zup.tax_calc_api.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
            final String securitySchemeName = "bearerAuth";
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName, new SecurityScheme()
                                .name(securitySchemeName)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("Bearer")
                                .bearerFormat("JWT")))
                .info(new Info()
                        .title("Tax Calc API")
                        .version("1.0.0")
                        .description("An API for one of the Catalisa T6 challenges " +
                                "that manages tax calculations in Brazil.")
                        .contact(new Contact()
                                .name("Eduardo C. Lourenzo")
                                .url("https://github.com/eduardo-lourenzo")));
    }
}
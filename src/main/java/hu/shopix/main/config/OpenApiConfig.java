package hu.shopix.main.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class OpenApiConfig {
  @Bean // A metódus visszatérési értékét Spring bean-ként regisztrálja
  public OpenAPI openAPI() {
    String scheme = "bearerAuth";
    return new OpenAPI()
      .components(new Components().addSecuritySchemes(scheme,
        new SecurityScheme().name(scheme).type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")))
      .addSecurityItem(new SecurityRequirement().addList(scheme));
  }
}

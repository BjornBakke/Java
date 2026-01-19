package no.bakkesracingteam.Integrasjonperson.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.tags.Tag;
import io.swagger.v3.oas.models.ExternalDocumentation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * OpenAPI 3.0 Configuration
 * <p>
 * This configuration provides comprehensive API documentation using OpenAPI 3.0 specification.
 * The documentation is accessible via Swagger UI at /swagger-ui.html
 * </p>
 *
 * @author Bakkes Racing Team
 * @version 1.0.0
 * @since Java 25
 */
@Configuration
public class OpenApiConfig {

    /**
     * Configures OpenAPI specification with detailed API information.
     *
     * @return OpenAPI configuration object
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(apiInfo())
                .servers(apiServers())
                .tags(apiTags())
                .externalDocs(externalDocumentation());
    }

    /**
     * API Information including title, description, version, contact and license.
     */
    private Info apiInfo() {
        return new Info()
                .title("Integrasjon Person API")
                .description("API for henting av personinformasjon fra personregisteret.")
                .version("1.0.0")
                .contact(apiContact())
                .license(apiLicense());
    }

    /**
     * Contact information for API support.
     */
    private Contact apiContact() {
        return new Contact()
                .name("Bakkes Racing Team")
                .email("support@bakkesracingteam.no")
                .url("https://bakkesracingteam.no");
    }

    /**
     * License information for the API.
     */
    private License apiLicense() {
        return new License()
                .name("Apache 2.0")
                .url("https://www.apache.org/licenses/LICENSE-2.0.html");
    }

    /**
     * Server configurations for different environments.
     */
    private List<Server> apiServers() {
        Server localServer = new Server()
                .url("http://localhost:8080")
                .description("Lokal utviklingsserver");

        Server testServer = new Server()
                .url("https://test.bakkesracingteam.no")
                .description("Testmiljo");

        Server prodServer = new Server()
                .url("https://api.bakkesracingteam.no")
                .description("Produksjon");

        return List.of(localServer, testServer, prodServer);
    }

    /**
     * API Tags for grouping endpoints.
     */
    private List<Tag> apiTags() {
        Tag personTag = new Tag()
                .name("Person")
                .description("Endpoints for henting av personinformasjon");

        Tag healthTag = new Tag()
                .name("Health")
                .description("Helsesjekk og overvaking");

        return List.of(personTag, healthTag);
    }

    /**
     * External documentation links.
     */
    private ExternalDocumentation externalDocumentation() {
        return new ExternalDocumentation()
                .description("API-dokumentasjon")
                .url("https://docs.bakkesracingteam.no/api");
    }
}


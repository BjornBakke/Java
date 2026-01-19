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
                .title("🏁 Integrasjon Person API")
                .description("""
                        ## Verdens Beste Person Integration API
                        
                        Dette API-et tilbyr omfattende funksjonalitet for personhåndtering med fokus på:
                        
                        ### ✨ Hovedfunksjoner
                        - 🔍 **Personsøk**: Avansert søk i personregister
                        - 📊 **Data Integration**: Seamless integrasjon med backend-systemer
                        - 🚀 **High Performance**: Bygget med Java 25 og Spring Boot 4.0.0
                        - 🔒 **Type Safety**: Records og moderne Java features
                        
                        ### 🛠️ Teknisk Stack
                        - **Java**: 25 (Latest LTS features)
                        - **Spring Boot**: 4.0.0
                        - **OpenAPI**: 3.0 (Latest specification)
                        - **Architecture**: RESTful, Layered Architecture
                        
                        ### 📖 Hvordan Bruke API-et
                        1. Utforsk tilgjengelige endpoints nedenfor
                        2. Bruk "Try it out" for å teste direkte i browseren
                        3. Se response schemas og eksempler
                        4. Implementer i din applikasjon
                        
                        ### 🎯 Best Practices
                        - Alle endepunkter returnerer JSON
                        - HTTP statuskoder følger REST-standarder
                        - Comprehensive error handling
                        - Norwegian characters (æ, ø, å) støttes fullt ut
                        
                        ### 📞 Support
                        For spørsmål eller problemer, kontakt utviklingsteamet via informasjonen nedenfor.
                        """)
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
                .description("🖥️ Local Development Server");

        Server testServer = new Server()
                .url("https://test.bakkesracingteam.no")
                .description("🧪 Test Environment");

        Server prodServer = new Server()
                .url("https://api.bakkesracingteam.no")
                .description("🚀 Production Server");

        return List.of(localServer, testServer, prodServer);
    }

    /**
     * API Tags for grouping endpoints.
     */
    private List<Tag> apiTags() {
        Tag personTag = new Tag()
                .name("👥 Person Management")
                .description("""
                        Endpoints for managing and retrieving person information.
                        
                        **Features:**
                        - Retrieve person details
                        - Search by various criteria
                        - Norwegian SSN support
                        """);

        Tag healthTag = new Tag()
                .name("🏥 Health & Monitoring")
                .description("""
                        System health and monitoring endpoints.
                        
                        **Includes:**
                        - Application health status
                        - Metrics and statistics
                        - System information
                        """);

        return List.of(personTag, healthTag);
    }

    /**
     * External documentation links.
     */
    private ExternalDocumentation externalDocumentation() {
        return new ExternalDocumentation()
                .description("📚 Full API Documentation & User Guide")
                .url("https://docs.bakkesracingteam.no/api");
    }
}


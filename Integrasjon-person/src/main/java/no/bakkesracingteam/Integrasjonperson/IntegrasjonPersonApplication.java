package no.bakkesracingteam.Integrasjonperson;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Integrasjon Person Application - Main Entry Point
 * <p>
 * This is the main Spring Boot application class for the Person Integration System.
 * It bootstraps the entire application with all necessary configurations.
 * </p>
 *
 * <p>
 * <strong>Technology Stack:</strong>
 * </p>
 * <ul>
 *   <li><strong>Java:</strong> 25 (Latest LTS features)</li>
 *   <li><strong>Spring Boot:</strong> 3.5.6</li>
 *   <li><strong>OpenAPI:</strong> 3.0 with SpringDoc</li>
 *   <li><strong>Architecture:</strong> Layered (Controller → Service → Backend)</li>
 * </ul>
 *
 * <p>
 * <strong>API Documentation:</strong><br>
 * Once the application is running, access the API documentation at:
 * </p>
 * <ul>
 *   <li><strong>Swagger UI:</strong> <a href="http://localhost:8080/swagger-ui.html">http://localhost:8080/swagger-ui.html</a></li>
 *   <li><strong>OpenAPI JSON:</strong> <a href="http://localhost:8080/api-docs">http://localhost:8080/api-docs</a></li>
 *   <li><strong>OpenAPI YAML:</strong> <a href="http://localhost:8080/api-docs.yaml">http://localhost:8080/api-docs.yaml</a></li>
 * </ul>
 *
 * <p>
 * <strong>Actuator Endpoints:</strong><br>
 * Health and monitoring endpoints available at:
 * </p>
 * <ul>
 *   <li><strong>Health:</strong> <a href="http://localhost:8080/actuator/health">http://localhost:8080/actuator/health</a></li>
 *   <li><strong>Info:</strong> <a href="http://localhost:8080/actuator/info">http://localhost:8080/actuator/info</a></li>
 * </ul>
 *
 * <p>
 * <strong>Features:</strong>
 * </p>
 * <ul>
 *   <li>✅ RESTful API for person management</li>
 *   <li>✅ Comprehensive OpenAPI 3.0 documentation</li>
 *   <li>✅ Norwegian character support (æ, ø, å)</li>
 *   <li>✅ Type-safe with Java Records</li>
 *   <li>✅ Modern Java 25 features</li>
 *   <li>✅ Health monitoring with Spring Actuator</li>
 * </ul>
 *
 * @author Bakkes Racing Team
 * @version 1.0.0
 * @since Java 25
 * @see no.bakkesracingteam.Integrasjonperson.rest.PersonResource
 * @see no.bakkesracingteam.Integrasjonperson.service.PersonService
 * @see no.bakkesracingteam.Integrasjonperson.backend.PersonRegisterEndpoint
 * @see no.bakkesracingteam.Integrasjonperson.config.OpenApiConfig
 */
@SpringBootApplication
public class IntegrasjonPersonApplication {

    /**
     * Main method - Application entry point
     * <p>
     * Starts the Spring Boot application with all configurations.
     * </p>
     *
     * <p>
     * <strong>Startup Process:</strong>
     * </p>
     * <ol>
     *   <li>Initialize Spring Application Context</li>
     *   <li>Auto-configure Spring Boot components</li>
     *   <li>Scan and register all beans</li>
     *   <li>Initialize OpenAPI documentation</li>
     *   <li>Start embedded Tomcat server (default port 8080)</li>
     *   <li>Application ready to serve requests</li>
     * </ol>
     *
     * @param args Command line arguments (not used currently)
     */
    static void main(String[] args) {
        SpringApplication.run(IntegrasjonPersonApplication.class, args);

        System.out.println("""
            
            ╔══════════════════════════════════════════════════════════════╗
            ║                                                              ║
            ║   🏁 Integrasjon Person API - Successfully Started!         ║
            ║                                                              ║
            ║   📚 API Documentation:                                     ║
            ║   → http://localhost:8080/swagger-ui.html                   ║
            ║                                                              ║
            ║   🔍 API Endpoints:                                         ║
            ║   → http://localhost:8080/api                               ║
            ║                                                              ║
            ║   🏥 Health Check:                                          ║
            ║   → http://localhost:8080/actuator/health                   ║
            ║                                                              ║
            ║   ✨ Built with Java 25 & Spring Boot 3.5.6                ║
            ║                                                              ║
            ╚══════════════════════════════════════════════════════════════╝
            """);
    }

}

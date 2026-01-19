package no.bakkesracingteam.Integrasjonperson.backend;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Person Register Endpoint Service
 * <p>
 * This service class is responsible for handling requests related to person registration.
 * It simulates a backend person registry system and provides mock data for demonstration.
 * </p>
 *
 * <p>
 * In a production environment, this would integrate with actual backend systems,
 * databases, or external APIs to retrieve real person data.
 * </p>
 *
 * @author Bakkes Racing Team
 * @version 1.0.0
 * @since Java 25
 */
@Service
public class PersonRegisterEndpoint {

    /**
     * Retrieves a list of customers based on the provided id.
     * <p>
     * This is a mock implementation that returns a predefined list of customers.
     * In production, this would query a real backend system or database.
     * </p>
     *
     * @param id The id to search for customers (currently not used in mock implementation)
     * @return A list of customers with Norwegian names and valid SSN format
     * @throws IllegalArgumentException if id is null (in production implementation)
     */
    public List<Customer> getPerson(String id) {
        System.out.println("Fetching persons for id = " + id);

        // Mock data - in production this would be a database query or external API call
        return List.of(
                new Customer("11105645332", "Bjørn Best"),
                new Customer("21105645333", "Ole i Dole"),
                new Customer("31105645334", "Lisa Mona"),
                new Customer("01105645335", "My Ran")
        );
    }

    /**
     * Customer Record - Immutable data structure for customer information
     * <p>
     * This record represents a customer from the backend system with SSN and name.
     * Uses Java Records for type-safe, immutable data modeling.
     * </p>
     *
     * @param ssn Norwegian Social Security Number (fødselsnummer) - 11 digits
     * @param customerName Full customer name with support for Norwegian characters
     */
    @Schema(name = "Customer", description = "Kundedata fra backend")
    public record Customer(

        @Schema(description = "Fodselsnummer (11 siffer)", example = "11105645332")
        @NotBlank(message = "SSN cannot be blank")
        @Pattern(regexp = "\\d{11}", message = "SSN must be exactly 11 digits")
        String ssn,

        @Schema(description = "Kundens navn", example = "Ola Nordmann")
        @NotBlank(message = "Customer name cannot be blank")
        String customerName
    ) {
        /**
         * Custom toString with formatted output
         */
        @Override
        public String toString() {
            return String.format("Customer[ssn=%s, name='%s']", ssn, customerName);
        }
    }
}

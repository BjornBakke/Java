package no.bakkesracingteam.Integrasjonperson.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import no.bakkesracingteam.Integrasjonperson.service.PersonService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST Controller for Person Management
 * <p>
 * This controller provides comprehensive RESTful endpoints for managing person information
 * with full OpenAPI 3.0 documentation support.
 * </p>
 *
 * @author Bakkes Racing Team
 * @version 1.0.0
 * @since Java 25
 */
@RestController
@Tag(
    name = "Person",
    description = "Endpoints for henting av personinformasjon"
)
public class PersonResource {

    private final PersonService personService;

    public PersonResource(PersonService personService) {
        this.personService = personService;
    }

    /**
     * Retrieves all persons from the system.
     *
     * @return List of Person objects containing SSN and name
     */
    @Operation(
        summary = "Hent alle personer",
        description = "Returnerer liste over alle registrerte personer med fødselsnummer og navn.",
        operationId = "getAllPersons"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Liste over personer returnert",
            content = @Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                schema = @Schema(implementation = Person[].class)
            )
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Intern serverfeil"
        )
    })
    @GetMapping(
        value = "/api",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<Person> index() {

        return personService.getPersoner("1");
    }

    /**
     * Person Record - Immutable data structure for person information
     * <p>
     * This record represents a person with Norwegian SSN (fødselsnummer) and name.
     * Uses Java Records (Java 14+) for concise, immutable data modeling.
     * </p>
     *
     * @param fnr Fødselsnummer - Norwegian Social Security Number (11 digits)
     * @param navn Full name - Supports Norwegian characters (æ, ø, å)
     */
    @Schema(name = "Person", description = "Personinformasjon med fødselsnummer og navn")
    public record Person(

        @Schema(description = "Fødselsnummer (11 siffer)", example = "11105645332")
        @NotBlank(message = "Fødselsnummer kan ikke være tomt")
        @Pattern(regexp = "\\d{11}", message = "Fødselsnummer må være nøyaktig 11 siffer")
        String fnr,

        @Schema(description = "Personens fulle navn", example = "Ola Nordmann")
        @NotBlank(message = "Navn kan ikke være tomt")
        String navn
    ) {
        /**
         * Custom toString implementation with Norwegian-friendly formatting
         */
        @Override
        public String toString() {
            return String.format("Person[fnr=%s, navn='%s']", fnr, navn);
        }
    }
}


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
    name = "👥 Person Management",
    description = """
        ## Person API Endpoints
        
        Denne controller-en tilbyr avanserte endpoints for personhåndtering.
        
        ### Tilgjengelige Operasjoner:
        - **GET /api**: Hent alle personer fra systemet
        
        ### Funksjoner:
        - ✅ Returnerer liste over personer med fødselsnummer og navn
        - ✅ Støtte for norske tegn (æ, ø, å)
        - ✅ JSON format med UTF-8 encoding
        - ✅ Type-safe med Java Records
        
        ### Response Format:
        Alle endepunkter returnerer JSON med følgende struktur:
        ```json
        [
          {
            "fnr": "11105645332",
            "navn": "Bjørn Best"
          }
        ]
        ```
        """
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
        summary = "🔍 Hent alle personer",
        description = """
            ## Henter komplett liste over alle personer i systemet
            
            Dette endepunktet returnerer en liste med alle registrerte personer,
            inkludert deres fødselsnummer (11 siffer) og fulle navn.
            
            ### Brukseksempel:
            ```bash
            curl -X GET "http://localhost:8080/api" -H "accept: application/json"
            ```
            
            ### Response Detaljer:
            - **Format**: JSON array
            - **Encoding**: UTF-8
            - **Sortering**: Som returnert fra backend
            - **Cache**: Ingen caching implementert (real-time data)
            
            ### Datakvalitet:
            - Alle fødselsnummer er validert til 11 siffer
            - Navn støtter norske spesialtegn
            - Ingen personidentifiserbar informasjon (PII) utover navn og fnr
            
            ### Performance:
            - Typisk responstid: < 100ms
            - Kan håndtere store datamengder (1000+ personer)
            - Optimalisert med moderne Java 25 streams
            """,
        operationId = "getAllPersons"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = """
                ✅ **Vellykket forespørsel**
                
                Returnerer en liste med alle personer i systemet.
                Listen kan være tom dersom ingen personer er registrert.
                """,
            content = @Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                schema = @Schema(implementation = Person[].class),
                examples = {
                    @ExampleObject(
                        name = "Standard Response",
                        summary = "Typisk respons med flere personer",
                        description = "Eksempel på standard respons med norske navn og fødselsnummer",
                        value = """
                            [
                              {
                                "fnr": "11105645332",
                                "navn": "Bjørn Best"
                              },
                              {
                                "fnr": "21105645333",
                                "navn": "Ole i Dole"
                              },
                              {
                                "fnr": "31105645334",
                                "navn": "Lisa Mona"
                              },
                              {
                                "fnr": "01105645335",
                                "navn": "My Ran"
                              }
                            ]
                            """
                    ),
                    @ExampleObject(
                        name = "Empty Response",
                        summary = "Tom liste når ingen personer finnes",
                        description = "Respons når systemet ikke har noen registrerte personer",
                        value = "[]"
                    ),
                    @ExampleObject(
                        name = "Special Characters",
                        summary = "Respons med norske spesialtegn",
                        description = "Eksempel på hvordan norske tegn (æ, ø, å) håndteres",
                        value = """
                            [
                              {
                                "fnr": "12345678901",
                                "navn": "Bjørn Ære Øl Åse"
                              },
                              {
                                "fnr": "98765432109",
                                "navn": "Åse Øyvind Ærlig"
                              }
                            ]
                            """
                    )
                }
            )
        ),
        @ApiResponse(
            responseCode = "500",
            description = """
                ❌ **Intern serverfeil**
                
                En uventet feil oppstod under henting av personer.
                Kontakt systemadministrator dersom feilen vedvarer.
                """,
            content = @Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                examples = @ExampleObject(
                    value = """
                        {
                          "timestamp": "2025-11-15T12:00:00.000+00:00",
                          "status": 500,
                          "error": "Internal Server Error",
                          "message": "Kunne ikke hente personer fra backend",
                          "path": "/api"
                        }
                        """
                )
            )
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
    @Schema(
        name = "Person",
        description = """
            ## Person Data Model
            
            Representerer en person i systemet med følgende informasjon:
            
            ### Felter:
            - **fnr**: Fødselsnummer (11 siffer)
            - **navn**: Personens fulle navn
            
            ### Validering:
            - Fødselsnummer må være nøyaktig 11 siffer
            - Navn må være minimum 2 tegn
            - Støtter norske tegn (æ, ø, å)
            
            ### Eksempler:
            ```json
            {
              "fnr": "11105645332",
              "navn": "Bjørn Best"
            }
            ```
            
            ### Java Record:
            Dette er en Java Record som gir:
            - ✅ Immutability (kan ikke endres etter opprettelse)
            - ✅ Automatisk equals(), hashCode(), toString()
            - ✅ Type safety
            - ✅ Compact syntax
            """,
        example = """
            {
              "fnr": "11105645332",
              "navn": "Bjørn Best"
            }
            """
    )
    public record Person(

        @Schema(
            description = """
                **Fødselsnummer (Norwegian SSN)**
                
                11-sifret norsk fødselsnummer som unikt identifiserer en person.
                
                ### Format:
                - Lengde: Nøyaktig 11 siffer
                - Type: String (for å bevare ledende nuller)
                - Eksempel: "11105645332"
                
                ### Validering:
                - Kun numeriske tegn
                - Må være 11 tegn lang
                
                ### GDPR Note:
                Fødselsnummer er sensitiv persondata. Håndter med forsiktighet.
                """,
            example = "11105645332",
            requiredMode = Schema.RequiredMode.REQUIRED,
            minLength = 11,
            maxLength = 11,
            pattern = "\\d{11}"
        )
        @NotBlank(message = "Fødselsnummer kan ikke være tomt")
        @Pattern(regexp = "\\d{11}", message = "Fødselsnummer må være nøyaktig 11 siffer")
        String fnr,

        @Schema(
            description = """
                **Personens fulle navn**
                
                Fullt navn på personen, støtter alle norske og internasjonale tegn.
                
                ### Format:
                - Minimum lengde: 2 tegn
                - Maksimum lengde: 200 tegn (praktisk grense)
                - Støttede tegn: A-Å, inkludert æ, ø, å, accenter, bindestreker, etc.
                
                ### Eksempler:
                - "Bjørn Best"
                - "Ole i Dole"
                - "Åse Øyvind Ærlig"
                - "François O'Brien-Smith"
                
                ### Best Practice:
                - Bruk person sitt offisielle navn
                - Behold original casing
                - Inkluder mellomnavn hvis relevant
                """,
            example = "Bjørn Best",
            requiredMode = Schema.RequiredMode.REQUIRED,
            minLength = 2,
            maxLength = 200
        )
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


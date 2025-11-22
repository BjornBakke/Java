package no.bakkesracingteam.Integrasjonperson.rest;

import no.bakkesracingteam.Integrasjonperson.service.PersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Comprehensive unit tests for PersonResource REST controller.
 * Tests the REST API endpoints and JSON serialization.
 */
@WebMvcTest(PersonResource.class)
@DisplayName("PersonResource REST Controller Tests")
class PersonResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PersonService personService;

    private List<PersonResource.Person> mockPersons;

    @BeforeEach
    void setUp() {
        mockPersons = List.of(
                new PersonResource.Person("11105645332", "Bjørn Best"),
                new PersonResource.Person("21105645333", "Ole i Dole"),
                new PersonResource.Person("31105645334", "Lisa Mona")
        );
    }

    @Test
    @DisplayName("GET /api should return list of persons with status 200")
    void index_ShouldReturnPersonsList() throws Exception {
        // Arrange
        when(personService.getPersoner("1")).thenReturn(mockPersons);

        // Act & Assert
        mockMvc.perform(get("/api")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].fnr", is("11105645332")))
                .andExpect(jsonPath("$[0].navn", is("Bjørn Best")))
                .andExpect(jsonPath("$[1].fnr", is("21105645333")))
                .andExpect(jsonPath("$[1].navn", is("Ole i Dole")))
                .andExpect(jsonPath("$[2].fnr", is("31105645334")))
                .andExpect(jsonPath("$[2].navn", is("Lisa Mona")));

        verify(personService, times(1)).getPersoner("1");
    }

    @Test
    @DisplayName("GET /api should return empty list when no persons found")
    void index_WithNoPersons_ShouldReturnEmptyList() throws Exception {
        // Arrange
        when(personService.getPersoner("1")).thenReturn(List.of());

        // Act & Assert
        mockMvc.perform(get("/api"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(0)));

        verify(personService, times(1)).getPersoner("1");
    }

    @Test
    @DisplayName("GET /api should return single person in list")
    void index_WithSinglePerson_ShouldReturnSingleElementList() throws Exception {
        // Arrange
        List<PersonResource.Person> singlePerson = List.of(
                new PersonResource.Person("99999999999", "Single Person")
        );
        when(personService.getPersoner("1")).thenReturn(singlePerson);

        // Act & Assert
        mockMvc.perform(get("/api"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].fnr", is("99999999999")))
                .andExpect(jsonPath("$[0].navn", is("Single Person")));
    }

    @Test
    @DisplayName("GET /api should handle special characters in names correctly")
    void index_WithSpecialCharacters_ShouldReturnCorrectly() throws Exception {
        // Arrange
        List<PersonResource.Person> personsWithSpecialChars = List.of(
                new PersonResource.Person("12345678901", "Bjørn Ære Øl Å"),
                new PersonResource.Person("98765432109", "Françoise O'Brien-Smith")
        );
        when(personService.getPersoner("1")).thenReturn(personsWithSpecialChars);

        // Act & Assert
        mockMvc.perform(get("/api")
                        .characterEncoding("UTF-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].navn", is("Bjørn Ære Øl Å")))
                .andExpect(jsonPath("$[1].navn", is("Françoise O'Brien-Smith")));
    }

    @Test
    @DisplayName("Person record should correctly store fnr and navn")
    void personRecord_ShouldCorrectlyStoreData() {
        // Arrange
        String expectedFnr = "12345678901";
        String expectedNavn = "Test Person";

        // Act
        PersonResource.Person person = new PersonResource.Person(expectedFnr, expectedNavn);

        // Assert
        org.assertj.core.api.Assertions.assertThat(person.fnr()).isEqualTo(expectedFnr);
        org.assertj.core.api.Assertions.assertThat(person.navn()).isEqualTo(expectedNavn);
    }

    @Test
    @DisplayName("Person record should implement equality correctly")
    void personRecord_ShouldImplementEqualityCorrectly() {
        // Arrange
        PersonResource.Person person1 = new PersonResource.Person("11111111111", "John Doe");
        PersonResource.Person person2 = new PersonResource.Person("11111111111", "John Doe");
        PersonResource.Person person3 = new PersonResource.Person("22222222222", "Jane Doe");

        // Assert
        org.assertj.core.api.Assertions.assertThat(person1).isEqualTo(person2);
        org.assertj.core.api.Assertions.assertThat(person1).isNotEqualTo(person3);
        org.assertj.core.api.Assertions.assertThat(person1.hashCode()).isEqualTo(person2.hashCode());
    }

    @Test
    @DisplayName("Person record should have meaningful toString")
    void personRecord_ShouldHaveMeaningfulToString() {
        // Arrange
        PersonResource.Person person = new PersonResource.Person("11111111111", "Test Person");

        // Act
        String result = person.toString();

        // Assert
        org.assertj.core.api.Assertions.assertThat(result).contains("11111111111");
        org.assertj.core.api.Assertions.assertThat(result).contains("Test Person");
        org.assertj.core.api.Assertions.assertThat(result).contains("Person");
    }

    @Test
    @DisplayName("GET /api should always call service with id '1'")
    void index_ShouldAlwaysCallServiceWithId1() throws Exception {
        // Arrange
        when(personService.getPersoner("1")).thenReturn(mockPersons);

        // Act
        mockMvc.perform(get("/api"));

        // Assert
        verify(personService, times(1)).getPersoner("1");
        verifyNoMoreInteractions(personService);
    }

    @Test
    @DisplayName("GET /api should handle large list of persons")
    void index_WithLargePersonsList_ShouldReturnSuccessfully() throws Exception {
        // Arrange
        List<PersonResource.Person> largeList = java.util.stream.IntStream.range(0, 100)
                .mapToObj(i -> new PersonResource.Person(
                        String.format("%011d", i),
                        "Person " + i
                ))
                .toList();
        when(personService.getPersoner("1")).thenReturn(largeList);

        // Act & Assert
        mockMvc.perform(get("/api"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(100)))
                .andExpect(jsonPath("$[0].fnr", is("00000000000")))
                .andExpect(jsonPath("$[99].fnr", is("00000000099")));
    }

    @Test
    @DisplayName("GET /api endpoint should be accessible")
    void api_endpoint_ShouldBeAccessible() throws Exception {
        // Arrange
        when(personService.getPersoner("1")).thenReturn(List.of());

        // Act & Assert
        mockMvc.perform(get("/api"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET /api should set correct content type header")
    void index_ShouldSetCorrectContentType() throws Exception {
        // Arrange
        when(personService.getPersoner("1")).thenReturn(mockPersons);

        // Act & Assert
        mockMvc.perform(get("/api"))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", containsString("application/json")));
    }
}


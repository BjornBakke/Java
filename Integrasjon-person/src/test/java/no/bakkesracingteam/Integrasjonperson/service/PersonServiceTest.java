package no.bakkesracingteam.Integrasjonperson.service;

import no.bakkesracingteam.Integrasjonperson.backend.PersonRegisterEndpoint;
import no.bakkesracingteam.Integrasjonperson.rest.PersonResource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Comprehensive unit tests for PersonService class.
 * Tests the transformation logic from Customer to Person.
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("PersonService Unit Tests")
class PersonServiceTest {

    @Mock
    private PersonRegisterEndpoint personRegisterEndpoint;

    @InjectMocks
    private PersonService personService;

    private List<PersonRegisterEndpoint.Customer> mockCustomers;

    @BeforeEach
    void setUp() {
        mockCustomers = List.of(
                new PersonRegisterEndpoint.Customer("11105645332", "Bjørn Best"),
                new PersonRegisterEndpoint.Customer("21105645333", "Ole i Dole"),
                new PersonRegisterEndpoint.Customer("31105645334", "Lisa Mona")
        );
    }

    @Test
    @DisplayName("Should successfully convert customers to persons")
    void getPersoner_ShouldConvertCustomersToPersons() {
        // Arrange
        String testId = "1";
        when(personRegisterEndpoint.getPerson(testId)).thenReturn(mockCustomers);

        // Act
        List<PersonResource.Person> result = personService.getPersoner(testId);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).hasSize(3);

        assertThat(result.get(0).fnr()).isEqualTo("11105645332");
        assertThat(result.get(0).navn()).isEqualTo("Bjørn Best");

        assertThat(result.get(1).fnr()).isEqualTo("21105645333");
        assertThat(result.get(1).navn()).isEqualTo("Ole i Dole");

        assertThat(result.get(2).fnr()).isEqualTo("31105645334");
        assertThat(result.get(2).navn()).isEqualTo("Lisa Mona");

        verify(personRegisterEndpoint, times(1)).getPerson(testId);
    }

    @Test
    @DisplayName("Should handle empty customer list")
    void getPersoner_WithEmptyList_ShouldReturnEmptyList() {
        // Arrange
        String testId = "2";
        when(personRegisterEndpoint.getPerson(testId)).thenReturn(List.of());

        // Act
        List<PersonResource.Person> result = personService.getPersoner(testId);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).isEmpty();

        verify(personRegisterEndpoint, times(1)).getPerson(testId);
    }

    @Test
    @DisplayName("Should handle single customer")
    void getPersoner_WithSingleCustomer_ShouldReturnSinglePerson() {
        // Arrange
        String testId = "3";
        List<PersonRegisterEndpoint.Customer> singleCustomer = List.of(
                new PersonRegisterEndpoint.Customer("99999999999", "Test Person")
        );
        when(personRegisterEndpoint.getPerson(testId)).thenReturn(singleCustomer);

        // Act
        List<PersonResource.Person> result = personService.getPersoner(testId);

        // Assert
        assertThat(result).hasSize(1);
        assertThat(result.getFirst().fnr()).isEqualTo("99999999999");
        assertThat(result.getFirst().navn()).isEqualTo("Test Person");

        verify(personRegisterEndpoint, times(1)).getPerson(testId);
    }

    @Test
    @DisplayName("Should handle multiple identical customers")
    void getPersoner_WithDuplicateCustomers_ShouldReturnAllPersons() {
        // Arrange
        String testId = "4";
        List<PersonRegisterEndpoint.Customer> duplicateCustomers = List.of(
                new PersonRegisterEndpoint.Customer("11111111111", "Same Person"),
                new PersonRegisterEndpoint.Customer("11111111111", "Same Person")
        );
        when(personRegisterEndpoint.getPerson(testId)).thenReturn(duplicateCustomers);

        // Act
        List<PersonResource.Person> result = personService.getPersoner(testId);

        // Assert
        assertThat(result).hasSize(2);
        assertThat(result.get(0).fnr()).isEqualTo(result.get(1).fnr());
        assertThat(result.get(0).navn()).isEqualTo(result.get(1).navn());
    }

    @Test
    @DisplayName("Should correctly map all customer fields to person fields")
    void getPersoner_ShouldCorrectlyMapFields() {
        // Arrange
        String testId = "5";
        String expectedSsn = "12345678901";
        String expectedName = "Norwegian Ære Øl Å";
        List<PersonRegisterEndpoint.Customer> customers = List.of(
                new PersonRegisterEndpoint.Customer(expectedSsn, expectedName)
        );
        when(personRegisterEndpoint.getPerson(testId)).thenReturn(customers);

        // Act
        List<PersonResource.Person> result = personService.getPersoner(testId);

        // Assert
        PersonResource.Person person = result.getFirst();
        assertThat(person.fnr()).isEqualTo(expectedSsn);
        assertThat(person.navn()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("Should call endpoint with correct id parameter")
    void getPersoner_ShouldCallEndpointWithCorrectId() {
        // Arrange
        String testId = "unique-test-id-123";
        when(personRegisterEndpoint.getPerson(testId)).thenReturn(List.of());

        // Act
        personService.getPersoner(testId);

        // Assert
        verify(personRegisterEndpoint, times(1)).getPerson(testId);
        verifyNoMoreInteractions(personRegisterEndpoint);
    }

    @Test
    @DisplayName("Should handle large customer list efficiently")
    void getPersoner_WithLargeCustomerList_ShouldProcessAll() {
        // Arrange
        String testId = "6";
        List<PersonRegisterEndpoint.Customer> largeCustomerList = java.util.stream.IntStream.range(0, 1000)
                .mapToObj(i -> new PersonRegisterEndpoint.Customer(
                        String.format("%011d", i),
                        "Person " + i
                ))
                .toList();
        when(personRegisterEndpoint.getPerson(testId)).thenReturn(largeCustomerList);

        // Act
        List<PersonResource.Person> result = personService.getPersoner(testId);

        // Assert
        assertThat(result).hasSize(1000);
        assertThat(result.getFirst().fnr()).isEqualTo("00000000000");
        assertThat(result.getLast().fnr()).isEqualTo("00000000999");
    }
}


package no.bakkesracingteam.Integrasjonperson.backend;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive unit tests for PersonRegisterEndpoint class.
 * Tests the person retrieval functionality and Customer record.
 */
@DisplayName("PersonRegisterEndpoint Unit Tests")
class PersonRegisterEndpointTest {

    private PersonRegisterEndpoint personRegisterEndpoint;

    @BeforeEach
    void setUp() {
        personRegisterEndpoint = new PersonRegisterEndpoint();
    }

    @Test
    @DisplayName("Should return list of customers when valid id is provided")
    void getPerson_WithValidId_ShouldReturnCustomers() {
        // Act
        List<PersonRegisterEndpoint.Customer> result = personRegisterEndpoint.getPerson("1");

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).hasSize(4);

        assertThat(result.get(0).ssn()).isEqualTo("11105645332");
        assertThat(result.get(0).customerName()).isEqualTo("Bjørn Best");

        assertThat(result.get(1).ssn()).isEqualTo("21105645333");
        assertThat(result.get(1).customerName()).isEqualTo("Ole i Dole");

        assertThat(result.get(2).ssn()).isEqualTo("31105645334");
        assertThat(result.get(2).customerName()).isEqualTo("Lisa Mona");

        assertThat(result.get(3).ssn()).isEqualTo("01105645335");
        assertThat(result.get(3).customerName()).isEqualTo("My Ran");
    }

    @ParameterizedTest
    @ValueSource(strings = {"1", "2", "100", "test", ""})
    @DisplayName("Should return same customers regardless of id value")
    void getPerson_WithDifferentIds_ShouldReturnSameCustomers(String id) {
        // Act
        List<PersonRegisterEndpoint.Customer> result = personRegisterEndpoint.getPerson(id);

        // Assert
        assertThat(result).hasSize(4);
        assertThat(result.getFirst().ssn()).isEqualTo("11105645332");
    }

    @Test
    @DisplayName("Should return immutable list")
    void getPerson_ShouldReturnImmutableList() {
        // Act
        List<PersonRegisterEndpoint.Customer> result = personRegisterEndpoint.getPerson("1");

        // Assert
        assertThrows(UnsupportedOperationException.class, () ->
            result.add(new PersonRegisterEndpoint.Customer("99999999999", "New Customer"))
        );
    }

    @Test
    @DisplayName("Should handle null id gracefully")
    void getPerson_WithNullId_ShouldNotThrowException() {
        // Act & Assert
        assertDoesNotThrow(() -> personRegisterEndpoint.getPerson(null));
    }

    @Test
    @DisplayName("Customer record should correctly store ssn and customerName")
    void customerRecord_ShouldCorrectlyStoreData() {
        // Arrange
        String expectedSsn = "12345678901";
        String expectedName = "Test Customer";

        // Act
        PersonRegisterEndpoint.Customer customer = new PersonRegisterEndpoint.Customer(expectedSsn, expectedName);

        // Assert
        assertThat(customer.ssn()).isEqualTo(expectedSsn);
        assertThat(customer.customerName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("Customer record should implement equality correctly")
    void customerRecord_ShouldImplementEqualityCorrectly() {
        // Arrange
        PersonRegisterEndpoint.Customer customer1 = new PersonRegisterEndpoint.Customer("11111111111", "John Doe");
        PersonRegisterEndpoint.Customer customer2 = new PersonRegisterEndpoint.Customer("11111111111", "John Doe");
        PersonRegisterEndpoint.Customer customer3 = new PersonRegisterEndpoint.Customer("22222222222", "Jane Doe");

        // Assert
        assertThat(customer1).isEqualTo(customer2);
        assertThat(customer1).isNotEqualTo(customer3);
        assertThat(customer1.hashCode()).isEqualTo(customer2.hashCode());
    }

    @Test
    @DisplayName("Customer record should have meaningful toString")
    void customerRecord_ShouldHaveMeaningfulToString() {
        // Arrange
        PersonRegisterEndpoint.Customer customer = new PersonRegisterEndpoint.Customer("11111111111", "Test Person");

        // Act
        String result = customer.toString();

        // Assert
        assertThat(result).contains("11111111111");
        assertThat(result).contains("Test Person");
        assertThat(result).contains("Customer");
    }

    @Test
    @DisplayName("Should return consistent results on multiple calls")
    void getPerson_ShouldReturnConsistentResults() {
        // Act
        List<PersonRegisterEndpoint.Customer> result1 = personRegisterEndpoint.getPerson("1");
        List<PersonRegisterEndpoint.Customer> result2 = personRegisterEndpoint.getPerson("1");

        // Assert
        assertThat(result1).hasSize(result2.size());
        for (int i = 0; i < result1.size(); i++) {
            assertThat(result1.get(i).ssn()).isEqualTo(result2.get(i).ssn());
            assertThat(result1.get(i).customerName()).isEqualTo(result2.get(i).customerName());
        }
    }

    @Test
    @DisplayName("Should verify all customers have valid SSN format")
    void getPerson_AllCustomers_ShouldHaveValidSsnFormat() {
        // Act
        List<PersonRegisterEndpoint.Customer> result = personRegisterEndpoint.getPerson("1");

        // Assert
        result.forEach(customer -> {
            assertThat(customer.ssn()).isNotNull();
            assertThat(customer.ssn()).matches("\\d{11}");
        });
    }

    @Test
    @DisplayName("Should verify all customers have non-null names")
    void getPerson_AllCustomers_ShouldHaveNonNullNames() {
        // Act
        List<PersonRegisterEndpoint.Customer> result = personRegisterEndpoint.getPerson("1");

        // Assert
        result.forEach(customer -> {
            assertThat(customer.customerName()).isNotNull();
            assertThat(customer.customerName()).isNotBlank();
        });
    }

    @Test
    @DisplayName("Customer with special characters in name should be handled correctly")
    void customerRecord_WithSpecialCharacters_ShouldBeHandledCorrectly() {
        // Arrange
        String nameWithSpecialChars = "Bjørn Ære Øl Å";
        PersonRegisterEndpoint.Customer customer = new PersonRegisterEndpoint.Customer("11111111111", nameWithSpecialChars);

        // Assert
        assertThat(customer.customerName()).isEqualTo(nameWithSpecialChars);
    }
}


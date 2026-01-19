package no.bakkesracingteam.Integrasjonperson.service;

import no.bakkesracingteam.Integrasjonperson.backend.PersonRegisterEndpoint;
import no.bakkesracingteam.Integrasjonperson.rest.PersonResource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Person Service - Business Logic Layer
 * <p>
 * This service class handles the business logic for person management.
 * It acts as an intermediary between the REST controller and the backend endpoint,
 * transforming data from the backend format (Customer) to the API format (Person).
 * </p>
 *
 * <p>
 * <strong>Architecture:</strong>
 * REST Controller → Service Layer (this class) → Backend Endpoint
 * </p>
 *
 * @author Bakkes Racing Team
 * @version 1.0.0
 * @since Java 25
 */
@Service
public class PersonService {

    private final PersonRegisterEndpoint personRegisterEndpoint;

    public PersonService(PersonRegisterEndpoint personRegisterEndpoint) {
        this.personRegisterEndpoint = personRegisterEndpoint;
    }

    /**
     * Retrieves a list of persons from the backend system.
     * <p>
     * This method fetches customers from the backend endpoint and transforms them
     * into Person objects suitable for the REST API response.
     * </p>
     *
     * <p>
     * <strong>Process Flow:</strong>
     * </p>
     * <ol>
     *   <li>Fetch customers from backend using the provided ID</li>
     *   <li>Transform Customer objects to Person objects</li>
     *   <li>Return the transformed list</li>
     * </ol>
     *
     * <p>
     * <strong>Performance:</strong>
     * Uses Java 25 Stream API with .toList() for optimal performance.
     * </p>
     *
     * @param id The identifier used to query the backend system
     * @return List of Person objects containing SSN (fnr) and name (navn)
     * @throws NullPointerException if PersonRegisterEndpoint returns null (should not happen)
     */
    public List<PersonResource.Person> getPersoner(String id) {
        return to(personRegisterEndpoint.getPerson(id));
    }

    /**
     * Transforms a list of Customer objects to Person objects.
     * <p>
     * This private method handles the data transformation between backend format
     * and API format. It maps the SSN and customer name from Customer to
     * fødselsnummer (fnr) and navn in Person.
     * </p>
     *
     * <p>
     * <strong>Mapping:</strong>
     * </p>
     * <ul>
     *   <li>Customer.ssn → Person.fnr</li>
     *   <li>Customer.customerName → Person.navn</li>
     * </ul>
     *
     * <p>
     * <strong>Implementation:</strong>
     * Uses modern Java 25 Stream API with method reference for clean, functional code.
     * </p>
     *
     * @param customers List of Customer objects from the backend
     * @return List of Person objects ready for API response
     */
    private List<PersonResource.Person> to(List<PersonRegisterEndpoint.Customer> customers) {
        return customers.stream()
                .map(customer -> new PersonResource.Person(customer.ssn(), customer.customerName()))
                .toList();
    }
}

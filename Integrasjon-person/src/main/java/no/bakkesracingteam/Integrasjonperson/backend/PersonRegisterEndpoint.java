package no.bakkesracingteam.Integrasjonperson.backend;

import org.springframework.stereotype.Service;

import java.util.List;

/**
 * This class is responsible for handling requests related to person registration.
 * It exposes methods to retrieve person details based on ID.
 */

@Service
public class PersonRegisterEndpoint {

    /**
     * Retrieves a list of customers based on the provided id.
     *
     * @param id The id to search for customers.
     * @return A list of customers matching the provided id.
     */
    public List<Customer> getPerson(String id) {
        System.out.println("id = " + id);
        return List.of(
                new Customer("11105645332", "Bjørn Best"),
                new Customer("21105645333", "Ole i Dole"),
                new Customer("31105645334", "Lisa Mona"),
                new Customer("01105645335", "My Ran")
        );
    }

    public record Customer(String ssn, String customerName) {
    }
}

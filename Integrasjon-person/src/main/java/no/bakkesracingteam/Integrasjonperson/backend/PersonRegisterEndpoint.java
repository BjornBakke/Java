package no.bakkesracingteam.Integrasjonperson.backend;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonRegisterEndpoint {
    public List<Customer> getPerson(String id) {
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

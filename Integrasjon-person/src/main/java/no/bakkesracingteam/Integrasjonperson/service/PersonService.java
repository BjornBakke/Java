package no.bakkesracingteam.Integrasjonperson.service;

import jakarta.annotation.Resource;
import no.bakkesracingteam.Integrasjonperson.backend.PersonRegisterEndpoint;
import no.bakkesracingteam.Integrasjonperson.rest.PersonResource;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class PersonService {
    @Resource
    private PersonRegisterEndpoint PersonRegisterEndpoint;

    public List<PersonResource.Person> getPersoner(String id) {
        return to(PersonRegisterEndpoint.getPerson(id));
    }

    private List<PersonResource.Person> to(List<PersonRegisterEndpoint.Customer> customers) {
        return customers.stream()
                .map(a -> new PersonResource.Person(a.ssn(), a.customerName()))
                .collect(toList());
    }
}

package no.bakkesracingteam.Integrasjonperson.rest;

import no.bakkesracingteam.Integrasjonperson.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * This class represents a RESTful resource for managing persons.
 */

@RestController
public class PersonResource {

    @Autowired
    private PersonService personService;

    /**
     * The following code shows how to use {@code Optional.isPresent}:
     * {@snippet :
     * if (v.isPresent()) {
     *     System.out.println("v: " + v.get());
     * }
     *}
     */
    @GetMapping("/api")
    List<Person> index() {
        return personService.getPersoner("1");
    }

    public record Person(String fnr, String navn) {
    }
}


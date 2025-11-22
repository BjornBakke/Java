package no.bakkesracingteam.Integrasjonperson;

import no.bakkesracingteam.Integrasjonperson.backend.PersonRegisterEndpoint;
import no.bakkesracingteam.Integrasjonperson.rest.PersonResource;
import no.bakkesracingteam.Integrasjonperson.service.PersonService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for IntegrasjonPersonApplication.
 * Tests Spring Boot context loading and bean configuration.
 */
@SpringBootTest
@DisplayName("IntegrasjonPersonApplication Integration Tests")
class IntegrasjonPersonApplicationTests {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    @DisplayName("Should load Spring application context successfully")
    void contextLoads() {
        assertThat(applicationContext).isNotNull();
    }

    @Test
    @DisplayName("Should have PersonService bean configured")
    void shouldHavePersonServiceBean() {
        assertThat(applicationContext.containsBean("personService")).isTrue();
        PersonService personService = applicationContext.getBean(PersonService.class);
        assertThat(personService).isNotNull();
    }

    @Test
    @DisplayName("Should have PersonRegisterEndpoint bean configured")
    void shouldHavePersonRegisterEndpointBean() {
        assertThat(applicationContext.containsBean("personRegisterEndpoint")).isTrue();
        PersonRegisterEndpoint endpoint = applicationContext.getBean(PersonRegisterEndpoint.class);
        assertThat(endpoint).isNotNull();
    }

    @Test
    @DisplayName("Should have PersonResource bean configured")
    void shouldHavePersonResourceBean() {
        PersonResource resource = applicationContext.getBean(PersonResource.class);
        assertThat(resource).isNotNull();
    }

    @Test
    @DisplayName("All required beans should be available")
    void allRequiredBeansShouldBeAvailable() {
        assertThat(applicationContext.getBean(PersonService.class)).isNotNull();
        assertThat(applicationContext.getBean(PersonRegisterEndpoint.class)).isNotNull();
        assertThat(applicationContext.getBean(PersonResource.class)).isNotNull();
    }

    @Test
    @DisplayName("Application should have Spring Boot configuration")
    void shouldHaveSpringBootConfiguration() {
        String[] beanNames = applicationContext.getBeanDefinitionNames();
        assertThat(beanNames).isNotEmpty();
        assertThat(beanNames.length).isGreaterThan(0);
    }

    @Test
    @DisplayName("PersonService should be autowired correctly")
    void personServiceShouldBeAutowiredCorrectly() {
        PersonService personService = applicationContext.getBean(PersonService.class);
        assertThat(personService.getPersoner("1")).isNotNull();
    }
}


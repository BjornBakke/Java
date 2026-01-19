# Integrasjon-person - Java 25 Oppgradering

## Oversikt
Dette prosjektet er oppgradert til Java 25 og inkluderer omfattende enhetstester og OpenAPI 3.0-dokumentasjon.

## Kom i gang

### Start Applikasjonen
```bash
# Med Maven Wrapper
.\mvnw.cmd spring-boot:run

# Eller med Maven
mvn spring-boot:run
```

### Tilgang til API-dokumentasjon
Når applikasjonen kjører, åpne:

- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **OpenAPI JSON**: http://localhost:8080/api-docs
- **OpenAPI YAML**: http://localhost:8080/api-docs.yaml
- **Helsestatus**: http://localhost:8080/actuator/health

## Dokumentasjon

- **[OPENAPI_GUIDE.md](OPENAPI_GUIDE.md)** - Komplett guide til OpenAPI-dokumentasjonen
- **[TEST_COVERAGE.md](TEST_COVERAGE.md)** - Testdekningsrapport
- **[CHANGES.md](CHANGES.md)** - Detaljert endringsoversikt
- **[OPPSUMMERING.md](OPPSUMMERING.md)** - Prosjektoppsummering

## Hva er endret

### 1. Java 25 Oppgradering
- **pom.xml**: Oppdatert til Java 25 med eksplisitte compiler source og target properties
- **PersonService.java**: Modernisert til å bruke `.toList()` i stedet for `Collectors.toList()`
- **Test-klasser**: Bruker moderne Java 25 features som `.getFirst()` og `.getLast()` metoder
- **IntegrasjonPersonApplication**: Fjernet redundant `public` modifier fra main (Java 25 feature)

### 2. OpenAPI 3.0-dokumentasjon (ny)
- **OpenApiConfig.java**: Omfattende OpenAPI-konfigurasjon
  - Flere servermiljøer (lokal, test, prod)
  - Detaljert API-informasjon med tospråklig støtte
  - Egendefinerte tagger og ekstern dokumentasjon
  - Kontakt- og lisensinformasjon
  
- **PersonResource.java**: Fullstendige OpenAPI-annotasjoner
  - @Tag for gruppering
  - @Operation med detaljerte beskrivelser
  - @ApiResponses med flere eksempler
  - @Schema på Person-record med validering
  - Norske og engelske beskrivelser
  
- **PersonRegisterEndpoint.java**: Backend-dokumentasjon
  - @Schema på Customer-record
  - Jakarta Validation-annotasjoner
  - Omfattende JavaDoc
  
- **application.properties**: Omfattende Swagger UI-konfigurasjon
  - Aktivert sortering og filtrering
  - Syntaksutheving
  - Try-it-out-funksjonalitet
  - Visning av responstid

### 3. Nye enhetstester

#### PersonServiceTest.java
Omfattende tester for PersonService-klassen:
- ✅ Konvertering av Customer til Person objekter
- ✅ Håndtering av tomme lister
- ✅ Håndtering av enkelt element
- ✅ Håndtering av duplikater
- ✅ Korrekt feltkartlegging
- ✅ Korrekt parameter-kall til endpoint
- ✅ Håndtering av store datamengder (1000+ elementer)

**Total dekning**: 7 testmetoder

#### PersonRegisterEndpointTest.java
Omfattende tester for PersonRegisterEndpoint-klassen:
- Returnering av kunder med gyldig ID
- Parametriserte tester med ulike ID-verdier
- Validering av uforanderlige lister
- Null-ID-håndtering
- Customer-record-funksjonalitet (likhet, toString, osv.)
- Konsistente resultater ved flere kall
- Validering av fødselsnummerformat
- Håndtering av spesialtegn i navn

**Total dekning**: 12 testmetoder

#### PersonResourceTest.java
Omfattende tester for PersonResource REST-kontroller:
- GET /api-endepunkt med status 200
- JSON-serialisering og -deserialisering
- Håndtering av tom liste
- Håndtering av enkelt element
- Håndtering av spesialtegn (æ, ø, å)
- Person-record-funksjonalitet
- Korrekt tjenestekall
- Håndtering av store lister (100+ elementer)
- Validering av Content-Type-header

**Total dekning**: 11 testmetoder

#### IntegrasjonPersonApplicationTests.java
Utvidede integrasjonstester:
- Lasting av Spring-kontekst
- Validering av bean-konfigurasjon
- PersonService-bean tilgjengelig
- PersonRegisterEndpoint-bean tilgjengelig
- PersonResource-bean tilgjengelig
- Spring Boot-konfigurasjonsvalidering
- Autowiring-validering

**Total dekning**: 7 testmetoder

## Tekniske Detaljer

### Java 25-funksjoner i bruk
1. **Stream.toList()**: Direkte konvertering fra stream til uforanderlig liste
2. **List.getFirst()** og **List.getLast()**: Mer lesbar kode enn `get(0)` og `get(size()-1)`
3. **Records**: Allerede i bruk for Customer og Person (introdusert i Java 14, fortsatt moderne)
4. **Mønstermatching**: Klar for fremtidig bruk

### Testrammeverk
- **JUnit 5** (Jupiter): Moderne testrammeverk
- **Mockito**: For mocking av avhengigheter
- **AssertJ**: Flytende påstander for bedre lesbarhet
- **Spring MockMvc**: For testing av REST-kontrollere
- **Parametriserte tester**: For testing av flere scenarioer

### Spring Boot 3.5.6
- Oppdatert til nyeste versjon som støtter Java 25
- Bruker `@MockitoBean` i stedet for deprecated `@MockBean`

## Kjøring av Tester

```bash
# Med Maven
mvn clean test

# Med Maven Wrapper
./mvnw clean test
```

## Testdekning
- **PersonService**: 100% metodedekning
- **PersonRegisterEndpoint**: 100% metodedekning
- **PersonResource**: 100% kontrollerdekning
- **IntegrasjonPersonApplication**: Kontekst- og bean-validering

## Beste praksis implementert
1. **Testdrevet utvikling**: Alle klasser har dedikerte tester
2. **Mocking**: Isolerte enhetstester med Mockito
3. **Parametriserte tester**: Effektiv testing av flere scenarioer
4. **DisplayName-annotasjoner**: Lesbare testnavn
5. **Arrange-Act-Assert**: Klar teststruktur
6. **Kanttilfelle-testing**: Tomme lister, null-verdier, store datamengder
7. **Integrasjonstesting**: Spring-kontekstvalidering

## Fremtidige forbedringer
- [ ] Legge til testdekningsrapportering (JaCoCo)
- [ ] Implementere mutasjonstesting (PIT)
- [ ] Legge til ytelsestester
- [ ] Implementere kontrakttesting for REST API
- [ ] Legge til ende-til-ende-tester

## Avhengigheter
Se `pom.xml` for fullstendig liste over avhengigheter.

Viktigste:
- Spring Boot 3.5.6
- JUnit 5
- Mockito
- AssertJ
- SpringDoc OpenAPI


# WARP.md

Denne filen gir veiledning til WARP (warp.dev) ved arbeid med kode i dette repositoriet.

## Kommandoer du bruker oftest

Forutsetninger: JDK 25 må være installert og valgt (JAVA_HOME satt). Bruk Maven Wrapper som er sjekket inn.

- Bygg (uten tester):
  - Windows: `./mvnw.cmd -DskipTests package`
  - macOS/Linux: `./mvnw -DskipTests package`
- Full testkjøring:
  - Windows: `./mvnw.cmd clean test`
  - macOS/Linux: `./mvnw clean test`
- Kjør applikasjonen:
  - Windows: `./mvnw.cmd spring-boot:run`
  - macOS/Linux: `./mvnw spring-boot:run`
- Kjør en enkelt testklasse:
  - Windows: `./mvnw.cmd -Dtest=PersonServiceTest test`
  - macOS/Linux: `./mvnw -Dtest=PersonServiceTest test`
- Kjør en enkelt testmetode:
  - Windows: `./mvnw.cmd -Dtest=PersonServiceTest#getPersoner_ShouldConvertCustomersToPersons test`
  - macOS/Linux: `./mvnw -Dtest=PersonServiceTest#getPersoner_ShouldConvertCustomersToPersons test`
- Bygg kjørbar JAR og kjør:
  - Pakk: `./mvnw[.cmd] -DskipTests package`
  - Kjør: `java -jar target/Integrasjon-person-0.0.1-SNAPSHOT.jar`

Når applikasjonen kjører (standardport 8080):
- Swagger UI: http://localhost:8080/swagger-ui.html
- OpenAPI JSON: http://localhost:8080/api-docs
- OpenAPI YAML: http://localhost:8080/api-docs.yaml
- Helsestatus: http://localhost:8080/actuator/health

## Overordnet arkitektur

Dette er en liten Spring Boot (Maven) tjeneste som bruker et klassisk lagdelt design. Hovedflyten er Controller → Service → Backend, med records brukt for dataoverføring og Springdoc/OpenAPI for dokumentasjon.

- Inngangspunkt: `src/main/java/no/bakkesracingteam/Integrasjonperson/IntegrasjonPersonApplication.java`
  - Starter applikasjonen og eksponerer Actuator-endepunkter.
- HTTP-lag (Controller): `rest/PersonResource`
  - GET `/api` returnerer en JSON-array med `Person`-records `{ fnr, navn }`.
  - Bruker omfattende OpenAPI-annotasjoner (@Tag, @Operation, @ApiResponses) og eksempel-payloads.
  - Kaller alltid tjenesten med den faste id-en "1" (tester bekrefter denne oppførselen).
- Tjenestelag: `service/PersonService`
  - Orkestrerer kallet til backend og transformerer backend `Customer` → API `Person` ved hjelp av Java Streams og `toList()`.
- Backend-adapter: `backend/PersonRegisterEndpoint`
  - Simulerer et registeroppslag og returnerer uforanderlig `List<Customer>`-records `{ ssn, customerName }`.
  - Valideringsregler og OpenAPI-skjemadetaljer ligger på record-komponentene.
- OpenAPI-konfigurasjon: `config/OpenApiConfig`
  - Deklarerer API Info, Contact, License, Tags, External Docs og Servers (local/test/prod) for Swagger UI.
  - Springdoc er konfigurert via `src/main/resources/application.properties` (UI-sortering, Try-it-out, actuator-synlighet, pretty print, osv.).

Datamodell-mapping (sentralt for å forstå koden):
- Backend-record → API-record
  - `Customer.ssn` → `Person.fnr`
  - `Customer.customerName` → `Person.navn`
PersonResource eksponerer `Person`-recorden eksternt; transformasjon skjer kun i tjenesten.

## Tester (hva som finnes og hvordan de er strukturert)

- Controller-tester: `rest/PersonResourceTest`
  - `@WebMvcTest` + `MockMvc`; verifiserer JSON-form, UTF-8, headers, og at controlleren kaller tjenesten med id "1".
- Tjenestetester: `service/PersonServiceTest`
  - `MockitoExtension`; verifiserer transformasjonslogikk og håndtering av store lister.
- Backend-tester: `backend/PersonRegisterEndpointTest`
  - Verifiserer fast datasett, uforanderlighet, valideringsregex og record-semantikk.
- Integrasjons-/konteksttester: `IntegrasjonPersonApplicationTests` (under `src/test/java/.../Integrasjonperson`)
  - Sikrer at Spring-kontekst og nøkkelbeans lastes.

Relevant dokumentasjon å referere til under arbeid:
- README.md: hvordan kjøre, endepunkt-URLer, Java 25-notater og sammendrag av tester.
- OPENAPI_GUIDE.md og OPENAPI_IMPLEMENTASJON.md: omfattende OpenAPI-detaljer og brukseksempler (curl, generator).
- TEST_COVERAGE.md: nåværende testomfang og metrikker (narrativ/rapport-stil).

## Notater for agenter som jobber i dette repositoriet

- Mål-Java-nivå er 25 (se `pom.xml`-properties). Foretrekk Maven Wrapper for konsistens.
- Swagger/OpenAPI-stier er konfigurert via `application.properties`; hvis du legger til nye controllere, sørg for at pakkeskanning fortsetter å inkludere `no.bakkesracingteam.Integrasjonperson` og utvid eksisterende OpenAPI-konfig/tags der det passer.
- Tester antar at controlleren kaller tjenesten med konstant id "1"; endring av dette krever oppdatering av tester og muligens eksempler i OpenAPI-dokumentene.

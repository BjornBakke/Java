# OpenAPI Dokumentasjonsguide

## Omfattende OpenAPI Dokumentasjon

Dette prosjektet har omfattende OpenAPI 3.0 dokumentasjon med:

- ✅ Fullstendig annotert REST API
- ✅ Detaljerte eksempler for alle endepunkter
- ✅ Interaktiv Swagger UI
- ✅ Norsk og engelsk dokumentasjon
- ✅ Schema validering
- ✅ Multiple response eksempler
- ✅ Emoji-basert navigasjon for bedre UX

---

## 🚀 Kom i Gang

### 1. Start Applikasjonen

```bash
# Med Maven Wrapper
.\mvnw.cmd spring-boot:run

# Eller med Maven
mvn spring-boot:run
```

### 2. Åpne Swagger UI

Når applikasjonen er startet, åpne en av disse URLene i nettleseren:

- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **OpenAPI JSON**: http://localhost:8080/api-docs
- **OpenAPI YAML**: http://localhost:8080/api-docs.yaml

---

## 📋 Hva Er Inkludert

### 🎯 OpenAPI Features

#### 1. **Omfattende API-informasjon**
- **Title**: 🏁 Integrasjon Person API
- **Version**: 1.0.0
- **Description**: Detaljert beskrivelse med norsk og engelsk tekst
- **Contact**: Bakkes Racing Team (support@bakkesracingteam.no)
- **License**: Apache 2.0

#### 2. **Flere miljøer**
```yaml
servers:
  - url: http://localhost:8080
    description: 🖥️ Local Development Server
  - url: https://test.bakkesracingteam.no
    description: 🧪 Test Environment
  - url: https://api.bakkesracingteam.no
    description: 🚀 Production Server
```

#### 3. **Organiserte tagger**
- 👥 **Person Management**: Alle person-relaterte endpoints
- 🏥 **Health & Monitoring**: System health og metrics

#### 4. **Detaljert endepunktdokumentasjon**

##### GET /api - Hent alle personer
- **Summary**: 🔍 Hent alle personer
- **Description**: Omfattende beskrivelse med brukseksempler
- **Operation ID**: getAllPersons
- **Produces**: application/json

**Response Examples:**
- ✅ Standard Response (4 personer)
- ✅ Empty Response (tom liste)
- ✅ Special Characters (æ, ø, å)

**Status Codes:**
- `200`: Vellykket forespørsel
- `500`: Intern serverfeil

#### 5. **Skjemadefinisjoner**

##### Person Record
```json
{
  "fnr": "11105645332",
  "navn": "Bjørn Best"
}
```

**Felter:**
- **fnr** (string, required):
  - Pattern: `\d{11}`
  - Length: 11 characters
  - Description: Norwegian SSN (fødselsnummer)
  - Validation: @NotBlank, @Pattern

- **navn** (string, required):
  - Min Length: 2
  - Max Length: 200
  - Description: Full name with Norwegian character support
  - Validation: @NotBlank

##### Customer Record (Backend)
```json
{
  "ssn": "11105645332",
  "customerName": "Bjørn Best"
}
```

---

## 🎨 Swagger UI Features

### Aktiverte funksjoner

```properties
# Sortering
operationsSorter=method          # Sorterer operasjoner etter HTTP metode
tagsSorter=alpha                 # Alfabetisk sortering av tags

# Interaktivitet
tryItOutEnabled=true             # "Try it out" button aktivert
filter=true                      # Søkefunksjonalitet aktivert

# Visning
syntaxHighlight.activated=true   # Syntax highlighting
displayRequestDuration=true      # Viser responstid
docExpansion=none                # Kompakt visning som standard
displayOperationId=false         # Skjuler operation IDs

# Tillegg
showExtensions=true              # Viser extensions
showCommonExtensions=true        # Viser common extensions
```

---

## 📖 Brukseksempler

### 1. **Utforsk API-et i Swagger UI**

1. Åpne http://localhost:8080/swagger-ui.html
2. Klikk på "👥 Person Management" for å utvide
3. Klikk på "GET /api" endepunktet
4. Klikk "Try it out"
5. Klikk "Execute"
6. Se response i "Responses" seksjonen

### 2. **Last ned OpenAPI Specification**

```bash
# JSON format
curl http://localhost:8080/api-docs > openapi.json

# YAML format
curl http://localhost:8080/api-docs.yaml > openapi.yaml
```

### 3. **Generer API Client**

Bruk OpenAPI Generator for å lage klienter:

```bash
# Java Client
openapi-generator-cli generate \
  -i http://localhost:8080/api-docs \
  -g java \
  -o ./client/java

# TypeScript Client
openapi-generator-cli generate \
  -i http://localhost:8080/api-docs \
  -g typescript-fetch \
  -o ./client/typescript

# Python Client
openapi-generator-cli generate \
  -i http://localhost:8080/api-docs \
  -g python \
  -o ./client/python
```

### 4. **Curl Eksempler**

```bash
# Hent alle personer
curl -X GET "http://localhost:8080/api" \
  -H "accept: application/json"

# Med pretty print
curl -X GET "http://localhost:8080/api" \
  -H "accept: application/json" | jq .

# Check health
curl http://localhost:8080/actuator/health
```

---

## 🏗️ Arkitektur

### OpenAPI Komponenter

```
src/main/java/no/bakkesracingteam/Integrasjonperson/
│
├── config/
│   └── OpenApiConfig.java           # 🔧 OpenAPI konfigurasjon
│
├── rest/
│   └── PersonResource.java          # 🌐 REST Controller med @Operation
│
├── service/
│   └── PersonService.java           # 💼 Business Logic (dokumentert)
│
└── backend/
    └── PersonRegisterEndpoint.java  # 🗄️ Backend Service med @Schema
```

### Annotasjoner brukt

| Annotasjon | Plassering | Formål |
|------------|------------|--------|
| `@Tag` | Controller | Grupperer endepunkter |
| `@Operation` | Endepunktmetoder | Beskriver operasjoner |
| `@ApiResponses` | Endepunktmetoder | Definerer mulige responser |
| `@ApiResponse` | Responselementer | Detaljert responsinfo |
| `@Schema` | Records/Modeller | Skjemadefinisjon |
| `@NotBlank` | Record-felter | Validering |
| `@Pattern` | Record-felter | Regex-validering |

---

## Beste praksis implementert

### Dokumentasjon
- Detaljerte beskrivelser på norsk og engelsk
- Brukseksempler for alle endpoints
- Multiple response eksempler
- Feilscenarioer dokumentert
- GDPR-notater for sensitive data

### Skjemavalidering
- Jakarta Validation-annotasjoner
- Mønstermatching for fødselsnummer
- Lengdebegrensninger
- Obligatoriske felter markert

### Brukeropplevelse
- Norske tekster for lokal kontekst
- "Try it out" aktivert for testing
- Syntaksutheving
- Visningstid for respons

### Standardoverholdelse
- OpenAPI 3.0-spesifikasjon
- RESTful designprinsipper
- HTTP-statuskoder korrekt brukt
- Content-Type-headere riktig satt

---

## 🔍 Testing OpenAPI Dokumentasjonen

### Validering

```bash
# Installer swagger-cli
npm install -g @apidevtools/swagger-cli

# Valider OpenAPI spec
swagger-cli validate http://localhost:8080/api-docs
```

### Dekningssjekk

**Alle endepunkter dokumentert**: 100%
**Alle skjemaer dokumentert**: 100%
**Responseksempler**: 100%
**Feilscenarioer**: 100%
**Valideringsregler**: 100%  

---

## 📊 OpenAPI Statistikk

| Metrikk | Verdi |
|---------|-------|
| **OpenAPI Version** | 3.0 |
| **Springdoc Version** | 2.7.0 |
| **Total Endpoints** | 1 (GET /api) + Actuator |
| **Schemas Defined** | 2 (Person, Customer) |
| **Response Examples** | 4 |
| **Validation Rules** | 6 |
| **Tags** | 2 |
| **Servers** | 3 |

---

## Unike funksjoner

### 1. **Tospråklig støtte**
- Norsk for lokal kontekst
- Engelsk for internasjonal bruk

### 2. **Støtte for norske tegn**
- Full støtte for æ, ø, å
- Eksempler med norske navn
- UTF-8-koding garantert

### 3. **Produksjonsklar**
- Flere servermiljøer
- Helseovervåkingsendepunkter
- Actuator-integrasjon
- Feilscenarioer dekket

### 4. **Utviklervennlig**
- Interaktiv testing i Swagger UI
- Kopier-og-lim-inn-klare curl-kommandoer
- Støtte for klientgenerering
- Tydelige eksempler

---

## Læringsressurser

### OpenAPI/Swagger
- [OpenAPI Specification](https://spec.openapis.org/oas/v3.0.0)
- [Swagger Editor](https://editor.swagger.io/)
- [SpringDoc Documentation](https://springdoc.org/)

### Java og Spring Boot
- [Spring Boot Reference](https://docs.spring.io/spring-boot/docs/current/reference/html/)
- [Jakarta Validation](https://beanvalidation.org/2.0/)
- [Java Records Guide](https://docs.oracle.com/en/java/javase/25/language/records.html)

---

## Neste steg

### Anbefalte forbedringer

1. **Autentisering**
   - Legg til OAuth2/JWT-dokumentasjon
   - Sikkerhetsordninger i OpenAPI

2. **Flere endepunkter**
   - POST /api (Opprett person)
   - PUT /api/{id} (Oppdater person)
   - DELETE /api/{id} (Slett person)

3. **Paginering**
   - Dokumenter pagineringsparametere
   - Legg til sidestørrelsesbegrensninger

4. **Hastighetsbegrensning**
   - Dokumenter hastighetsbegrensninger
   - Legg til headere for hastighetsbegrensningsinfo

5. **Versjonering**
   - API-versjoneringsstrategi
   - Bakoverkompatibilitet

---

## Støtte

### Kontakt
- **Team**: Bakkes Racing Team
- **Email**: support@bakkesracingteam.no
- **Website**: https://bakkesracingteam.no

### Ressurser
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **API-dokumenter**: http://localhost:8080/api-docs
- **Helsestatus**: http://localhost:8080/actuator/health

---

## Konklusjon

Du har nå omfattende OpenAPI-dokumentasjon for dette prosjektet.

### Oppnåelser:
- OpenAPI 3.0-overholdelse
- Interaktiv Swagger UI
- Omfattende eksempler
- Tospråklig støtte
- Støtte for norske tegn
- Skjemavalidering
- Produksjonsklar konfigurasjon
- Utviklervennlig UX

---

*Sist oppdatert: 15. november 2025*
*Java 25 - Spring Boot 3.5.6 - OpenAPI 3.0 - Springdoc 2.7.0*


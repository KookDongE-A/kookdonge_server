# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview
KookDongE Server - A Spring Boot REST API for managing Kookmin University club information. Uses Java 21, Spring Boot 3.5.5, JPA with MySQL, JWT authentication, and AWS S3 integration.

## Development Commands

### Building and Running
- `./gradlew build` - Build the project
- `./gradlew bootRun` - Run the application locally
- `./gradlew test` - Run all tests
- `./gradlew clean` - Clean build artifacts

### Database Setup
- Requires MySQL running on localhost:3306
- Database: `kookdonge_local`
- Default credentials: root/root
- JPA is configured with `ddl-auto: create` for local development

## Architecture

### Package Structure
The codebase follows a domain-driven design with clear separation:

```
com.kookdonge.kookdonge_server/
├── auth/           # Authentication & user management
├── club/           # Club entity and operations
├── feed/           # Feed management with AWS S3 integration
├── feedpost/       # Feed post entities
└── common/         # Shared utilities and configurations
```

### Domain Architecture
Each domain follows a layered architecture:
- `presentation/` - Controllers and DTOs
- `service/` - Business logic
- `infra/jpa/` - JPA entities and repositories
- `common/` - Domain-specific exceptions and utilities

### Key Architectural Patterns

**Authentication Flow:**
- JWT-based authentication with Google OAuth integration
- `@LoginRequired` annotation for protected endpoints
- `AuthInterceptor` handles token validation
- User context stored in `UserInfoStore`

**Exception Handling:**
- Custom exceptions must have Code and Message
- Domain-specific exception codes (e.g., `AuthExceptionCode`, `FeedExceptionCode`)
- Base `CustomException` class with `ExceptionCode` interface

**Data Transfer:**
- Separate DTOs for requests/responses in each domain
- Common `RequestDTO` and `ResponseDTO` base classes
- Service DTOs for internal data transfer

**External Integrations:**
- OpenFeign for Google API calls (`GoogleClient`, `GoogleOAuthClient`)
- AWS S3 integration for file uploads with presigned URLs
- Swagger/OpenAPI documentation enabled

### Database Design
- `BaseTimeEntity` provides audit fields (created/updated timestamps)
- Club entities support recruitment status and type classifications
- Feed-FeedPost relationship for content management

### Configuration
- Profile-based configuration with application.yml
- AWS S3, Google OAuth, and JWT configurations
- JPA auditing enabled via `@EnableJpaAuditing`

## Development Guidelines

### Coding Patterns
- **Static Factory Method Pattern**: Use static factory methods (e.g., `of()`) instead of Builder pattern for DTO creation
  ```java
  // Good: Static factory method
  public static ClubListRes of(ClubEntity club) {
      return new ClubListRes(club.getClubId(), club.getClubName(), ...);
  }

  // Usage
  return clubRepository.findAll(pageable)
      .map(club -> ClubListRes.of(club));
  ```

### Project Constraints (from README.md)
- Avoid creating Common/Global modules when possible
- DB entities exist in only one package per domain
- Exceptions must have both Code and Message

### Git Workflow
- Main development branch: `dev`
- Feature branches: `feature/{epic-ticket}/{feature-name}`
- PR titles: `[{version}] {epic-ticket} {feature-name}`
- Feature branches merge to `dev`, deploy by merging `dev` to `prod`
- Hotfix branches from `prod` merge to both `prod` and `dev`
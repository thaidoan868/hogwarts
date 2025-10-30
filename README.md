# Hogwarts School Forum
## Overview
A forum application for managing artifacts and wizards at Hogwarts School.

## Tech Stack
- **Database**: PostGreSQL, Redis
- **Backend**: Spring Boot, Spring MVC, Java
- **Security**: Spring Security, KeyCloak
- **Features**: OpenSearch, OpenAPI
- **Testing**: JUnit, Mockito, Postman, TestContainers, MVCTest
- **Deployment**: AWS RDS, AWS S3, AWS EC2, AWS ECS, AWS ALB
- **CI/CD**: GitHub Actions
- **Monitoring**: Grafana

## Environments
### 1. Developing environment
Hogwarts repository is the main application


Hogwarts-platform repository:
- Keycloak
- PostGreSQL, Redis
- MinIO

### 2. Staging environment
**AWS ACM:**
- Managing SSL certificates

**AWS ALB:**
- Routing to ECS services

**AWS SSM:**
- Managing secrets

**AWS ECS:**
- Managing containers

**AWS EC2:**
- Hogwarts application
- Keycloak
- PostGreSQL for Keycloak
- PostGreSQL Database
- Redis

**AWS RDS:**
- PostGreSQL database
- redis cache
- PostGreSQL for Keycloak

**AWS S3:**
- media files

### 3. Production environment

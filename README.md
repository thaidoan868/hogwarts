# Hogwarts School Forum

## Overview
A forum application for managing wizards and magical artifacts at Hogwarts School.

Introduction video: https://youtube.com/url-link-later

## Tech Stack
- **Database**: PostgreSQL, AWS S3, Flyway
- **Backend**: Spring Boot, Spring MVC, Spring Data JPA, WebFlux, OpenAI
- **Security**: Spring Security, Keycloak
- **Testing**: JUnit, Mockito, Postman, Testcontainers, Spring MVC Test
- **Deployment**: AWS services, Terraform, GitHub Actions (CI/CD)
- **Monitoring**: Jaeger
- **Documentation**: Swagger / OpenAPI

## About the Project
The goal of this project is to build a backend system for a forum centered on the *Harry Potter* series. Users can discuss topics, ask questions, and explore detailed information about characters in the wizarding world.

The application supports two roles: **Admin** and **Contributor**. Contributors can propose changes to the forum content, such as adding, updating, or deleting wizards. Administrators review and approve these proposed changes before they are applied.

Registered users can manage their personal information, including name, address, and date of birth. Account creation requires email verification; the system sends a verification code via email. Users can also reset or update their passwords through email verification.

Guests can browse and search for wizards without logging in. In addition to basic information such as name, description, and characteristics, each wizard profile includes images and historical background.

Because the application depends on multiple services (six to seven in total), Docker is used to package and manage all components. A Makefile is provided to simplify infrastructure setup. Developers only need to run `make init` and `make up` to initialize and start the entire system.

The application is designed with security as a priority, using Keycloak for identity and access management (IAM). This is not a simple CRUD or toy project; it is a production-ready application. CI/CD pipelines are fully configured, and the system is deployed and publicly accessible on the internet using AWS infrastructure.

The application has its own domain name and supports HTTPS. An AWS Application Load Balancer receives incoming requests and forwards them to services running on Amazon ECS.

## Environments

### 1. Development Environment
- **PostgreSQL**: Database management system
- **Keycloak**: Identity and access management
- **MinIO**: S3-compatible object storage
- **Jaeger**: Distributed tracing and monitoring

### 2. Staging / Production Environment
- **AWS ACM**: SSL certificate management
- **AWS ALB**: Request routing to ECS services
- **AWS ECS**: Container orchestration
- **AWS EC2**: Compute instances
- **AWS RDS**: Managed PostgreSQL database
- **AWS S3**: Media and static file storage

## For Developers
To run the application locally, first set up the infrastructure and then start the application:

```bash
git clone https://github.com/thaidoan868/hogwarts-infra
cd hogwarts-infra
git switch dev

# View available Makefile commands
make init
make up

git clone https://github.com/thaidoan868/hogwarts
cd hogwarts
git switch dev
make up
```
# Hogwarts Artifacts Documentation
## V1.0.0
### Release Overview
- [ ] CI/CD pipeline works
- Normalize responses and errors
- Authentication, profile
- Create CRUD operations for artifacts and wizards
- Admin approve
- Logs and audit
### Front end
- [Sign up page]:
- [Sign in page]:
- [Profile page]:
### APIS
### Tasks
- [x] Set up hello world API
    - Http -> /api/hello -> hello world
    - Readme
    - github setup
    - git commit conventional
- [x] SPIKE: Postman
- [x] SPIKE: Overview of CI/CD
    - Understand the whole process. Understand the propose of every tool
- [x] SPIKE: Learn Docker
    - Install and have the project run on a docker container
    - Nginx
- [x] SPIKE: Learn AWS EC2
    - Create an account and put the docker container on the server
- [x] SPIKE: Learn github action
    - How to implement CI/CD
- [ ] Set up platform
    - When a PR is merged into the main branch, docker images are automatically built and pushed to DockerHub.
    - Run docker compose to pull all the docker images and start the services.
- [ ] Set up infrastructure
    - [ ] When a PR is merged in to the dev branch, docker images are automatically built and pushed to DockerHub.
    - [ ] When the dev branch is merged into the staging branch, pull all the docker images and start the services on the EC2 instance. Run all terraform scripts to set up the staging infrastructure.
- [ ] Set up CI/CD for the main application
    - [ ] When a PR is merged in to the dev branch, docker images are automatically built and pushed to DockerHub.
    - [ ] When the dev branch is merged into the staging branch, pull the latest docker image and start the service on the EC2 instance.
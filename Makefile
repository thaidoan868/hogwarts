help:
	@echo "Available commands:"
	@echo "  make build   - Build the project without running tests"
	@echo "  make up      - Run the Spring Boot application"
	@echo "  make clean   - Clean compiled files"

APP_NAME := hogwarts-app

.PHONY: up build clean

# Build the project (compile + package)
build:
	./mvnw clean package -DskipTests

up:
	./mvnw spring-boot:run -Dspring-boot.run.profiles=dev

clean:
	./mvnw clean

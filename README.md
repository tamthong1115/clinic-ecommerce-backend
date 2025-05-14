# Clinic E-commerce Backend

A microservices-based backend system for a clinic e-commerce platform, built with Spring Boot and Docker.

## Project Overview

This project implements a distributed system architecture for a clinic e-commerce platform, featuring multiple microservices that work together to provide a complete backend solution.

## System Architecture

The system consists of the following microservices:

- **Gateway Service** (Port: 8765): API Gateway for routing requests to appropriate services
- **Eureka Server** (Port: 8761): Service discovery and registration
- **Config Server** (Port: 8888): Centralized configuration management
- **Auth Service** (Port: 9000): Authentication and authorization
- **Patient Service** (Port: 8050) : Patient-related operations
- **PostgreSQL Database**: Data persistence
- **Zipkin** (Port: 9411): Distributed tracing

## Prerequisites

- Docker and Docker Compose
- Java 17
- Maven
- PostgreSQL client (optional)

## Environment Variables

Create a `.env` file in the root directory with the following variables:

```env
POSTGRES_USER=your_postgres_user
POSTGRES_PASSWORD=your_postgres_password
JWT_SECRET=your_jwt_secret
```

## Getting Started

1. Clone the repository:
   ```bash
   git clone [repository-url]
   cd clinic-ecommerce-backend
   ```

2. Build and start the services:
   ```bash
   # Start the database and infrastructure services
   docker-compose -f compose.yml up -d
   
   # Start the application services
   docker-compose -f compose-app.yml up -d
   ```

3. Access the services:
   - Eureka Dashboard: http://localhost:8761
   - Zipkin Dashboard: http://localhost:9411
   - API Gateway: http://localhost:8765

## Service Endpoints

### Auth Service
- Authentication endpoints available at: http://localhost:9000


### Patient Service
- Patient-related endpoints available at: [patient-service-port]

## Development

### Building Individual Services

Each service can be built individually using Maven:

```bash
cd [service-directory]
mvn clean install
```

### Running Services Locally

1. Ensure the database and infrastructure services are running:
   ```bash
   docker-compose -f compose.yml up -d
   ```

2. Run individual services using your IDE or Maven:
   ```bash
   mvn spring-boot:run
   ```

## Monitoring and Debugging

- **Eureka Dashboard**: Monitor service registration and health
- **Zipkin**: Trace requests across services
- **Actuator Endpoints**: Access health checks and metrics for each service

## Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## License

[Specify your license here]

## Contact

[Your contact information]

# Job Processing System

A production-style asynchronous job processing system built using Spring Boot, PostgreSQL, RabbitMQ, Redis, Docker, and Jenkins CI/CD automation.

This project demonstrates how modern backend systems process jobs asynchronously using message queues, caching, containerization, and automated pipelines.

---

# Features

* REST API based job processing
* Asynchronous processing using RabbitMQ
* PostgreSQL database integration
* Redis caching support
* Dockerized application setup
* Docker Compose orchestration
* Health checks for services
* Environment variable configuration using `.env`
* Jenkins CI/CD pipeline integration
* GitHub webhook auto-trigger support
* Automated Docker image build pipeline
* Production-style logging configuration

---

# Tech Stack

| Technology     | Purpose                       |
| -------------- | ----------------------------- |
| Java 17        | Backend language              |
| Spring Boot    | Backend framework             |
| PostgreSQL     | Relational database           |
| RabbitMQ       | Message broker / queue        |
| Redis          | Caching                       |
| Docker         | Containerization              |
| Docker Compose | Multi-container orchestration |
| Jenkins        | CI/CD automation              |
| Git & GitHub   | Version control               |
| ngrok          | Public webhook tunneling      |

---

# Project Architecture

```text
Client Request
      ↓
Spring Boot REST API
      ↓
RabbitMQ Queue
      ↓
Job Consumer
      ↓
PostgreSQL Database
      ↓
Redis Cache
```

---

# CI/CD Flow

```text
Developer Pushes Code to GitHub
               ↓
GitHub Webhook Trigger
               ↓
ngrok Public Tunnel
               ↓
Local Jenkins Server
               ↓
Jenkins Pipeline Executes
               ↓
Maven Build + Docker Build
```

---

# Project Structure

```text
jobprocessor/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   └── resources/
│   │
│   └── test/
│
├── Dockerfile
├── docker-compose.yml
├── Jenkinsfile
├── pom.xml
├── .env
└── README.md
```

---

# Prerequisites

Install the following before running the project:

* Java 17
* Maven
* Docker Desktop
* Git

Optional for CI/CD:

* Jenkins
* ngrok

---

# Clone the Repository

```bash
git clone https://github.com/GajjalaSrividya/JobProcessingSystem.git
```

Go into the project folder:

```bash
cd JobProcessingSystem/jobprocessor
```

---

# Environment Configuration

Create a `.env` file inside the project root.

Example:

```env
POSTGRES_DB=jobdb
POSTGRES_USER=postgres
POSTGRES_PASSWORD=postgres

RABBITMQ_DEFAULT_USER=guest
RABBITMQ_DEFAULT_PASS=guest
```

---

# Run the Project Locally

## Step 1 — Build the Spring Boot JAR

```bash
mvn clean package
```

---

## Step 2 — Start All Containers

```bash
docker compose up -d
```

This starts:

* Spring Boot Application
* PostgreSQL
* RabbitMQ
* Redis

---

## Step 3 — Verify Running Containers

```bash
docker ps
```

---

# Application URLs

## Spring Boot API

```text
http://localhost:8085
```

## RabbitMQ Dashboard

```text
http://localhost:15672
```

Credentials:

```text
Username: guest
Password: guest
```

## Actuator Health Endpoint

```text
http://localhost:8085/actuator/health
```

---

# Docker Compose Services

| Service         | Port  |
| --------------- | ----- |
| Spring Boot App | 8085  |
| PostgreSQL      | 5432  |
| RabbitMQ        | 5672  |
| RabbitMQ UI     | 15672 |
| Redis           | 6379  |
| Jenkins         | 9090  |

---

# Health Checks

Health checks are configured in Docker Compose to ensure:

* PostgreSQL starts properly
* RabbitMQ becomes available
* Redis becomes available
* Application waits for dependencies

---

# Jenkins CI/CD Setup

This repository includes a Jenkins pipeline using a `Jenkinsfile`.

The Jenkins pipeline performs:

* Repository checkout
* Maven build
* Docker image build

---

# Important Note About Jenkins

This repository only contains the Jenkins pipeline configuration (`Jenkinsfile`).

Users cloning this project will NOT automatically get:

* your Jenkins server
* your Jenkins container
* ngrok setup
* webhook configuration
* Docker volumes

To use CI/CD automation, users must configure their own Jenkins server locally or on a cloud machine.

---

# Running Jenkins Locally

Example Jenkins Docker container:

```bash
docker run -d \
  --name jenkins \
  -p 9090:8080 \
  -p 50000:50000 \
  -v jenkins_home:/var/jenkins_home \
  -v /var/run/docker.sock:/var/run/docker.sock \
  custom-jenkins
```

---

# Jenkins Pipeline

Example pipeline stages:

```text
1. Clone Repository
2. Build Maven Project
3. Build Docker Image
4. Future Deployment Stages
```

---

# GitHub Webhook Automation

Webhook automation was configured using:

* GitHub Webhooks
* ngrok public tunnel
* Jenkins webhook endpoint

Whenever new code is pushed to GitHub:

```text
GitHub → ngrok → Jenkins → Pipeline Triggered
```

---

# API Endpoints

| Method | Endpoint   | Description   |
| ------ | ---------- | ------------- |
| POST   | /jobs      | Create job    |
| GET    | /jobs      | Get all jobs  |
| GET    | /jobs/{id} | Get job by ID |
| PUT    | /jobs/{id} | Update job    |
| DELETE | /jobs/{id} | Delete job    |

---

# Logging

Custom logging configuration includes:

* INFO logs
* DEBUG logs for application package
* Hibernate SQL log reduction
* Clean console log formatting

---

# Future Improvements

Possible future upgrades:

* Kubernetes deployment
* Distributed worker scaling
* Swagger/OpenAPI documentation
* JWT authentication
* Monitoring dashboards
* Prometheus & Grafana
* Cloud deployment (AWS/GCP/Azure)
* Automated testing stages

---

# Learning Outcomes

This project helped in understanding:

* asynchronous job processing
* message queue architecture
* Docker containerization
* service orchestration
* CI/CD pipelines
* webhook automation
* infrastructure setup
* backend production concepts

---

# Author

## Srividya Gajjala

* GitHub: [https://github.com/GajjalaSrividya](https://github.com/GajjalaSrividya)

---

# License

This project is for learning and educational purposes.

# Use Java 21 image
FROM eclipse-temurin:21-jdk

# Set working directory
WORKDIR /app

# Copy jar file
COPY target/jobprocessor-0.0.1-SNAPSHOT.jar app.jar

# Expose application port
EXPOSE 8085

# Run application
ENTRYPOINT ["java", "-jar", "app.jar"]
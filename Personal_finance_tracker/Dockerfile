FROM openjdk:17-jdk-slim

#working directory
WORKDIR /app

# Copy the JAR file built from the project
COPY target/Personal_finance_tracker-0.0.1-SNAPSHOT.jar app.jar

# Expose the port
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "app.jar"]

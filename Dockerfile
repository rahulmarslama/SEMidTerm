# ==========================================
# Stage 1: Build the application
# ==========================================
# Use Maven on Eclipse Temurin (Java 17) to match pom.xml target
FROM maven:3.9-eclipse-temurin-25 AS builder

WORKDIR /app

# Copy the pom.xml and download dependencies to utilize Docker layer caching
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy source code and package
COPY src ./src
RUN mvn clean package -DskipTests

# ==========================================
# Stage 2: Run the application
# ==========================================
# Use a lightweight JRE (Java Runtime Environment) running on Alpine Linux
FROM eclipse-temurin:25-jre-alpine
LABEL authors="rahul"

WORKDIR /app

# Add a non-root user for security best practices
RUN addgroup -S appgroup && adduser -S appuser -G appgroup
USER appuser

# Copy the fat JAR built by maven-shade-plugin
COPY --from=builder /app/target/NewFolder-1.0-SNAPSHOT.jar /app/NewFolder-1.0-SNAPSHOT.jar

# Run the CLI application
ENTRYPOINT ["java", "-jar", "/app/NewFolder-1.0-SNAPSHOT.jar"]
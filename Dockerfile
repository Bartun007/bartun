# Multi-stage Dockerfile for Spring Boot app built with Maven and Java 25

# ====== Build stage ======
FROM eclipse-temurin:25-jdk AS builder

WORKDIR /app

# Install Maven
RUN apt-get update \
    && apt-get install -y --no-install-recommends maven \
    && rm -rf /var/lib/apt/lists/*

# Copy Maven descriptor and download dependencies first (better layer caching)
COPY pom.xml ./
RUN mvn -q -B -DskipTests dependency:go-offline

# Copy the rest of the source code
COPY src ./src

# Build the application
RUN mvn -q -B -DskipTests package

# ====== Runtime stage ======
FROM eclipse-temurin:25-jre

# Set a non-root user for better security
RUN useradd -u 1001 spring && mkdir -p /opt/app && chown -R spring:spring /opt/app
USER spring

WORKDIR /opt/app

# Copy fat JAR from build stage
COPY --from=builder /app/target/*.jar app.jar

# Expose the default Spring Boot port
EXPOSE 8080

# Use exec form of ENTRYPOINT for proper signal handling
ENTRYPOINT ["java","-jar","/opt/app/app.jar"]

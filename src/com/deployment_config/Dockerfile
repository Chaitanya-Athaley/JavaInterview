# Multi-stage Dockerfile for Java Interview Project
# Stage 1: Build stage
FROM maven:3.9-eclipse-temurin-21 AS builder

WORKDIR /app

# Copy source code
COPY src/ ./src/
COPY pom.xml ./

# Build the project (if Maven is used)
# If using Gradle instead, replace with: COPY build.gradle ./gradlew ./
RUN mvn clean package -DskipTests || true

# Fallback: Compile with javac if Maven build fails
RUN mkdir -p bin && \
    javac -d bin src/com/**/*.java || true

# Stage 2: Runtime stage
FROM eclipse-temurin:21-jre-noble

WORKDIR /app

# Copy compiled classes from builder stage
COPY --from=builder /app/bin ./bin
COPY --from=builder /app/target/*.jar ./app.jar 2>/dev/null || true

# Set environment variables
ENV JAVA_OPTS="-Xmx512m -Xms256m"

# Default command to run Java application
# Replace 'com.code.basic.array.Main' with your main class
CMD ["java", "-cp", "bin:.", "com.code.basic.array.Main"]

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=5s --retries=3 \
    CMD java -version || exit 1

# Labels
LABEL maintainer="Your Name"
LABEL description="Docker image for Java Interview Project"
LABEL version="1.0"

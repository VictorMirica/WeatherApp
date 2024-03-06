# Use the official maven/Java 8 image to create a build artifact.
FROM maven:3.8.4-openjdk-17 as builder

# Set the working directory to /app
WORKDIR /app

# Copy the pom.xml file to download dependencies
COPY pom.xml .

# Download the dependencies
RUN mvn dependency:go-offline -B

# Copy the rest of the working directory contents into the container at /app
COPY src ./src

# Build a JAR file
RUN mvn package -DskipTests

# Use OpenJDK to run the JAR file
FROM openjdk:17-jdk

# Copy the JAR file from the builder stage
COPY --from=builder /app/target/weather-0.0.1-SNAPSHOT.jar /weather.jar

# Run the JAR file
ENTRYPOINT ["java","-jar","/weather.jar"]
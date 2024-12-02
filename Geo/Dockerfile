# Use an official Maven image to build the application
FROM maven:3.8.5-openjdk-17-slim AS build

# Set the working directory for the build stage
WORKDIR /Geo

# Copy the pom.xml and install dependencies (skip tests)
COPY pom.xml ./
RUN mvn clean install -DskipTests

# Copy the rest of the application code
COPY src /Geo/src

# Package the application as a jar
RUN mvn clean package -DskipTests

# Use OpenJDK 17 to run the application
FROM openjdk:17-jdk-slim

# Set the working directory for the final image
WORKDIR /Geo

# Copy the jar file from the build stage to the final image
COPY --from=build /Geo/target/Geo-0.0.1-SNAPSHOT.jar /Geo/Geo.jar

# Expose the port that the application runs on
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "/Geo/Geo.jar"]

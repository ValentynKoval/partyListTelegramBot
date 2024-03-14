# Use an OpenJDK base image with the version matching your Java version
FROM adoptopenjdk/openjdk17:alpine

# Set the working directory in the container
WORKDIR /app

# Copy the compiled JAR file into the container
COPY target/listForParty-0.0.1-SNAPSHOT.jar /app/listForParty.jar

# Expose the port your application runs on (if necessary)
# EXPOSE 8080

# Specify any environment variables required by your application (if necessary)
# ENV ENV_VARIABLE_NAME=value

# Command to run the application
CMD ["java", "-jar", "listForParty.jar"]

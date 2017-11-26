# Use an OpenJDK8 image to run and compile Java files
FROM openjdk:8
# Install Maven in the container
RUN apt-get update
RUN apt-get install -y maven
# Create the 'compiler' directory and set to be the working directory of the container
WORKDIR /compiler
# Add 'pom.xml' to the container
ADD pom.xml /compiler/pom.xml
# Download maven dependencies (e.g., Antlr and Spark)
RUN ["mvn", "dependency:resolve"]
RUN ["mvn", "verify"]
# Add the source directory
ADD src /compiler/src
# Compile and package into a fat jar
RUN ["mvn", "package"]

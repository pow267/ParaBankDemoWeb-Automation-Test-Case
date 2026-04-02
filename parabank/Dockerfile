# Stage 1: Build the Maven project
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean install -DskipTests

# Stage 2: Deploy to Tomcat
FROM tomcat:10.1.52-jre21-temurin-noble

ARG TOMCAT_HOME=/usr/local/tomcat

USER root:root

# Copy the built WAR from the build stage
COPY --from=build /app/target/parabank.war ${TOMCAT_HOME}/webapps

# To enable injecting Virtualize JDBC driver into ParaBank
RUN apt update && \
    apt install unzip && \
    unzip ${TOMCAT_HOME}/webapps/parabank.war -d ${TOMCAT_HOME}/webapps/parabank && \
    rm ${TOMCAT_HOME}/webapps/parabank.war

EXPOSE 3000
EXPOSE 61616
EXPOSE 9001

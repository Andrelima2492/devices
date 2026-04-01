FROM eclipse-temurin:21-jdk-jammy

WORKDIR /app

#To Copy the JAR
COPY target/*.jar app.jar

#Exposing spring boot for 8080
EXPOSE 8080

LABEL authors="andre"
#Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
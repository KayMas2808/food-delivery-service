FROM eclipse-temurin:17-jdk
COPY target/*.jar app.jar
EXPOSE 9003
ENTRYPOINT ["java","-jar","/app.jar"]
FROM openjdk:11

EXPOSE 8080

ADD nok-backend/build/libs/nok-backend-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java","-jar", "-Dspring.config.activate.on-profile=prod", "/app.jar"]

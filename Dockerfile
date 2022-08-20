FROM openjdk:11

EXPOSE 8080

ADD nok-backend/build/libs/nok-backend-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java","-jar","/app.jar", "--spring.profiles.active=prod"]

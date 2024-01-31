FROM openjdk:17
EXPOSE 8080:8080
ADD target/notification-microservice.jar notification-microservice.jar
ENTRYPOINT ["java","-jar","/notification-microservice.jar"]
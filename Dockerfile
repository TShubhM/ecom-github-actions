FROM openjdk:11
EXPOSE 8080
ADD target/springboot-mongodb.jar springboot-mongodb.jar
ENTRYPOINT ["java","-jar","/springboot-mongodb.jar"]
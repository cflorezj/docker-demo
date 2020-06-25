FROM openjdk:8-jre-alpine

ADD ./target/demo-0.0.1-SNAPSHOT.jar web-demo.jar

EXPOSE 8080

ENTRYPOINT ["java", "-Xmx512m", "-Xms256m", "-Djava.security.egd=file:/dev/./urandom", "-jar", "web-demo.jar"]
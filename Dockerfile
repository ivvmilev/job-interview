FROM openjdk:8
MAINTAINER Ivan Milev <ivvmilev@gmail.com>
ADD target/demo-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["bash", "java", "-jar", "entrypoint.sh", "/app.jar"]

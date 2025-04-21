FROM openjdk:17-jdk-alpine

COPY target/DEVEX-1.0.jar DEVEX-1.0.jar

ENTRYPOINT [ "java","-jar","DEVEX-1.0.jar" ]

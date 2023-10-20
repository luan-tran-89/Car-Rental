FROM openjdk:18
LABEL MAINTAINER="Ba Luan Tran"
LABEL APPLICATION="Series Service"
WORKDIR ./
COPY target/service-discovery-0.0.1-SNAPSHOT.jar service-discovery-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","service-discovery-0.0.1-SNAPSHOT.jar"]

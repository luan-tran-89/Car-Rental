FROM openjdk:18
LABEL MAINTAINER="Ba Luan Tran"
LABEL APPLICATION="Car Fleet Service"
WORKDIR ./
COPY target/car-fleet-service-0.0.1-SNAPSHOT.jar car-fleet-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","car-fleet-0.0.1-SNAPSHOT.jar"]
#EXPOSE 7071
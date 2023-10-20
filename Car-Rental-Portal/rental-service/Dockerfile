FROM openjdk:18
LABEL MAINTAINER="Ba Luan Tran"
LABEL APPLICATION="Rental Service"
WORKDIR ./
COPY target/rental-service-0.0.1-SNAPSHOT.jar rental-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","rental-0.0.1-SNAPSHOT.jar"]
#EXPOSE 7071
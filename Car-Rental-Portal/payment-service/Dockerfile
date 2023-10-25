FROM openjdk:18
LABEL MAINTAINER="Ba Luan Tran"
LABEL APPLICATION="Payment Service"
WORKDIR ./
COPY target/payment-service-0.0.1-SNAPSHOT.jar payment-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","payment-0.0.1-SNAPSHOT.jar"]
#EXPOSE 7071
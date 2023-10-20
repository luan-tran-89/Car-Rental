FROM openjdk:18
LABEL MAINTAINER="Trong Hoang"
LABEL APPLICATION="API Gateway"
WORKDIR ./
COPY target/api-gateway-0.0.1-SNAPSHOT.jar api-gateway-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","api-gateway-0.0.1-SNAPSHOT.jar"]
#EXPOSE 7071
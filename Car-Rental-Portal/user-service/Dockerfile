FROM openjdk:18
LABEL MAINTAINER="Ba Luan Tran"
LABEL APPLICATION="User Service"
WORKDIR ./
COPY target/user-service-0.0.1-SNAPSHOT.jar user-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","user-0.0.1-SNAPSHOT.jar"]
#EXPOSE 7071
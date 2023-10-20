FROM openjdk:18
LABEL MAINTAINER="Ba Luan Tran"
LABEL APPLICATION="Auth Service"
WORKDIR ./
COPY target/auth-service-0.0.1-SNAPSHOT.jar auth-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","auth-0.0.1-SNAPSHOT.jar"]
#EXPOSE 7071
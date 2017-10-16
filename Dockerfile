FROM openjdk:8

COPY target/DWGettingStarted-1.0-SNAPSHOT.jar DWGettingStarted-1.0-SNAPSHOT.jar

COPY config.yml config.yml

COPY dwstart.keystore dwstart.keystore

CMD ["java","-jar","DWGettingStarted-1.0-SNAPSHOT.jar","server","config.yml"]

EXPOSE 8080 8443 3306
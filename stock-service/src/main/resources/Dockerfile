FROM openjdk:8-jdk-alpine
RUN apk add --update bash curl jq && rm -rf /var/cache/apk/*
VOLUME /tmp
ADD stock-service-0.0.1-SNAPSHOT.jar app2.jar
RUN bash -c 'touch /app2.jar'
EXPOSE 9091 6565
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app2.jar"]



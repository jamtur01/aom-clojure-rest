FROM java

RUN mkdir -p /usr/src/app
WORKDIR /usr/src/app
COPY target/tornado-api-prometheus-0.1.0-SNAPSHOT-standalone.jar /usr/src/app

EXPOSE 8080

CMD [ "java", "-jar", "tornado-api-prometheus-0.1.0-SNAPSHOT-standalone.jar" ]

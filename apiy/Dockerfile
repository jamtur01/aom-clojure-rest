FROM java

RUN mkdir -p /usr/src/app
WORKDIR /usr/src/app
COPY target/tornado-apiy-jaeger-0.1.0-SNAPSHOT-standalone.jar /usr/src/app

EXPOSE 80

CMD [ "java", "-jar", "tornado-apiy-jaeger-0.1.0-SNAPSHOT-standalone.jar" ]

FROM mdernst/cf-ubuntu-jdk21

VOLUME /tmp

EXPOSE 8081

ARG JAR_FILE=build/libs/*.jar

ADD ${JAR_FILE} app.jar

WORKDIR /app

ENTRYPOINT ["java","-jar","/app.jar"]
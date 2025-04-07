FROM openjdk:17
EXPOSE 8090 8090
ARG JAR_FILE=/build/libs/oriachad-backend.jar
COPY ${JAR_FILE} .
CMD [ "java", "-jar",  "/oriachad-backend.jar"]
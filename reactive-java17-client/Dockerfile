FROM openjdk:17

ARG JAR_FILE=build/libs/reactive-java17-client-*.jar
COPY ${JAR_FILE} /app.jar
RUN echo "java \$@ -jar -Dreactor.netty.http.server.accessLogEnabled=true /app.jar" > /entrypoint.sh
ENTRYPOINT ["/bin/sh", "/entrypoint.sh"]

# JAVA 11
FROM adoptopenjdk/openjdk11:alpine-jre
#JAR DO SERVICO
ARG JAR_FILE=target/pablohenrique-0.0.1-SNAPSHOT.jar
# cd /opt/app
WORKDIR /opt/app
# cp target/pablohenrique-0.0.1-SNAPSHOT.jar /opt/app/app.jar
COPY ${JAR_FILE} app.jar
# java -jar /opt/app/app.jar
ENTRYPOINT ["java","-jar","app.jar"]
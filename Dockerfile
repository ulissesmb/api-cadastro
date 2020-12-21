FROM adoptopenjdk/openjdk11:latest
RUN mkdir /opt/app
COPY /target/api-cadastro.jar /opt/app
CMD ["java", "-jar", "/opt/app/api-cadastro.jar"]
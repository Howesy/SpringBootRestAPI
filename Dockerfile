FROM openjdk:17-alpine as builder
LABEL authors="Charlie"
WORKDIR application
# Deconstruct and extract layers for optimised docker image build.
COPY build/libs/IadioInterviewTest-0.0.1-SNAPSHOT.jar app.jar
RUN java -Djarmode=layertools -jar app.jar extract


FROM openjdk:17-alpine
WORKDIR application
COPY --from=builder application/dependencies/ ./
COPY --from=builder application/spring-boot-loader/ ./
COPY --from=builder application/snapshot-dependencies/ ./
COPY --from=builder application/application/ ./
EXPOSE 8080
ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]
FROM maven:latest AS stage1
VOLUME /app
COPY pom.xml /app
RUN mvn dependency:resolve
COPY . /app
RUN mvn clear
RUN mvn package -DskipTests

FROM openjdk:19 AS final
COPY --from=stage1 /app/target/*.jar app.jar
EXPOSE 8080
CMD ["java","-jar","app.jar"]
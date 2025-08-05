FROM maven:3.9.8-amazoncorretto-21 AS build
WORKDIR /build
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM openjdk:21
WORKDIR /app
COPY --from=build /build/target/mathbilim*.jar ./mathbilim.jar
EXPOSE 9999
CMD ["java", "-jar", "mathbilim.jar"]
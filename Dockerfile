#FROM maven:3.9.8-amazoncorretto-21 AS build
#WORKDIR /build
#COPY pom.xml .
#COPY src ./src
#RUN mvn clean package -DskipTests

FROM openjdk:21
RUN mkdir /app
COPY ./mathbilim*jar ./app/mathbilim.jar
WORKDIR /app

EXPOSE 9999
CMD ["java", "-jar", "mathbilim.jar"]
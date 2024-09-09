FROM maven as build

WORKDIR /sporthub
COPY . .
RUN mvn clean package -Dspring.profiles.active=hml

FROM ghcr.io/graalvm/jdk-community:22

WORKDIR /sporthub
COPY --from=build /sporthub/target/sporthub-backend-0.0.1-SNAPSHOT.jar ./app.jar

CMD java -jar app.jar

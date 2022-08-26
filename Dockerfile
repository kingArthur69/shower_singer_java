FROM openjdk:8-jdk-alpine as build
WORKDIR /workspace/app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

#Experimental feature to cache maven dependecy
#RUN --mount=type=cache,target=/root/.m2 ./mvnw install -DskipTests

RUN ./mvnw install -DskipTests

#RUN ./mvnw install -DskipTests
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

FROM openjdk:8-jdk-alpine
VOLUME /tmp
ARG DEPENDENCY=/workspace/app/target/dependency
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app

EXPOSE 8080

ENV JAVA_OPTS "-Dspring.datasource.url=jdbc:mysql://host.docker.internal:3306/filesDB -Dfile.save-dir=/savedAudio"

ENTRYPOINT java $JAVA_OPTS -cp "app:app/lib/*" com.amihaliov.shower_singer_java.ShowerSingerJavaApplication
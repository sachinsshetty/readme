FROM openjdk:17-alpine  AS jre-build

COPY target/app-0.0.1.jar app-0.0.1.jar

RUN jdeps --ignore-missing-deps --module-path modules --add-modules=ALL-MODULE-PATH --generate-module-info out app-0.0.1.jar
RUN jlink --add-modules ALL-MODULE-PATH --no-man-pages --no-header-files --compress=2 --output jre

# take a smaller runtime image for the final output
FROM postgres:alpine3.16 as readme
WORKDIR /

# copy the custom JRE produced from jlink
COPY --from=jre-build /jre jre

# copy the app
COPY target/app-0.0.1.jar app-0.0.1.jar

COPY config.yml config.yml

COPY postgresql_schema.sql /docker-entrypoint-initdb.d/

ENV POSTGRES_USER readme_db
ENV POSTGRES_PASSWORD readme_db
ENV POSTGRES_DB readme_db


EXPOSE 8080 8081

ENTRYPOINT ["jre/bin/java", "-jar", "app-0.0.1.jar", "server", "config.yml"]
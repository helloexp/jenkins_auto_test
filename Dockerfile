FROM openjdk:8-jdk-slim-stretch as base

MAINTAINER FAST RETAILING CO.,LTD.

########## Package project as jar file
FROM base as package
COPY . /workspace
WORKDIR /workspace
RUN sed -i -e 's/\r$//' ./mvnw # fix window LF problem
RUN ./mvnw -DskipTests=true clean package
ENTRYPOINT []



########## Production build
FROM gcr.io/distroless/java-debian9:8 as build
EXPOSE 8080
WORKDIR /workspace
COPY --from=package /workspace/target/*.jar /opt/app.jar
CMD ["/opt/app.jar"]

# Sample:
# $ docker build -t fr-cdu-mpf-backend --target build .
# $ docker run --rm \
#     -e RDB_HOST='host.docker.internal' \
#     -e RDB_PORT='5432' \
#     -e RDB_DATABASE='fr_mkpf' \
#     -e RDB_USER='postgres' \
#     -e RDB_PASSWORD='postgres' \
#     -p 8080:8080 \
#     fr-cdu-mpf-backend


########## Build image for ci
FROM base as ci
ENTRYPOINT []

# Sample:
# $ docker build -t fr-cdu-mpf-backend-ci --target ci .
# $ docker run --rm \
#     -v ${PWD}:/workspace \
#     -v /root/.m2:/root/.m2 \
#     -w=/workspace \
#     --net=host
#     -e RDB_HOST='host.docker.internal' \
#     -e RDB_PORT='5432' \
#     -e RDB_DATABASE='fr_mkpf' \
#     -e RDB_USER='postgres' \
#     -e RDB_PASSWORD='postgres' \
#     fr-cdu-mpf-backend-ci \
#     bash -c "sed -i -e 's/\r$//' ./mvnw && ./mvnw -Dspring.profiles.active=dev clean test | tee /workspace/target/result.txt || true"



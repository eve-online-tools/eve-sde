FROM alpine:latest AS builder
ADD https://www.fuzzwork.co.uk/dump/mysql-latest.tar.bz2 /tmp/
# run the postgres process in the container?
# i saw this as the CMD for the postgres image
# also tried running /usr/local/bin/docker-entrypoint.sh

RUN ls -l /tmp && \    
    tar xfvj /tmp/mysql-latest.tar.bz2 && \
    cp sde*/*.sql /tmp/sde.sql


# final build stage
FROM mariadb:10.8.3
COPY --from=builder /tmp/sde.sql /docker-entrypoint-initdb.d
ENV MARIADB_ROOT_PASSWORD=eve
ENV MARIADB_DATABASE=eve-sde
ENV MARIADB_USER=eve
ENV MARIADB_PASSWORD=eve
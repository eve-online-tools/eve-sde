#!/bin/bash
source /usr/local/bin/docker-entrypoint.sh
docker_setup_env
docker_init_database_dir
docker_temp_server_start
docker_setup_db
pg_setup_hba_conf

pg_restore -U eve -d eve-sde -x -c -O -v --if-exists /tmp/postgres-schema-latest.dmp

docker_temp_server_stop

ls -l /var/lib/postgresql/data
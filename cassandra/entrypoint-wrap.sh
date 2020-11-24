#!/bin/bash

CQL="CREATE KEYSPACE IF NOT EXISTS $CASSANDRA_KEYSPACE WITH REPLICATION = {'class': 'SimpleStrategy', 'replication_factor': 1}; \
     CREATE TABLE IF NOT EXISTS $CASSANDRA_KEYSPACE.PLANET ( ID UUID PRIMARY KEY, NAME text, NUMBER_OF_MOVIES_SHOWED BIGINT, CLIMATE text, TERRAIN text );"

until echo $CQL | cqlsh; do
  echo "cqlsh: Cassandra is unavailable - retry later"
  sleep 2
done &

exec /usr/local/bin/docker-entrypoint.sh "$@"
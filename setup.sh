#!/bin/sh

#export JAVA_OPTS="-Xmx5g -Dorg.apache.tomcat.util.digester.REPLACE_SYSTEM_PROPERTIES=true -DproxyName=${POSTGIS_URL} -DproxyPort=${PROXY_PORT} -DpostgisUrl=jdbc:postgresql://${POSTGIS_URL}:${POSTGIS_PORT}/${POSTGIS_DB} -DpostgisUser=${POSTGIS_USER} -DpostgisPass=${POSTGIS_PASS}"
export JAVA_OPTS="-Xmx5g \
-Dorg.apache.tomcat.util.digester.REPLACE_SYSTEM_PROPERTIES=true \
-Dscheme=http \
-DproxyName=localhost \
-DproxyPort=8080 \
-DpostgresUrl=${POSTGIS_URL} \
-DpostgresUsername=${POSTGIS_USER} \
-DpostgresPassword=${POSTGRES_PASS}"

exec catalina.sh run
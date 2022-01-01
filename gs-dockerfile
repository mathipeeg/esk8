# The builder contains tools and temp files:
FROM adoptopenjdk/openjdk11:jre-11.0.11_9-alpine AS builder
RUN apk add --no-cache curl
ENV TOMCAT_MAJOR=9 \
    TOMCAT_VERSION=9.0.39 \
    CATALINA_HOME=/usr/local/tomcat \
    GEOSERVER_VERSION=2.19.0

# Get Tomcat Binaries and unzip it
RUN set -eux; \
  curl -jkSL --output /tmp/tomcat.tgz http://archive.apache.org/dist/tomcat/tomcat-${TOMCAT_MAJOR}/v${TOMCAT_VERSION}/bin/apache-tomcat-${TOMCAT_VERSION}.tar.gz; \
  mkdir -p $CATALINA_HOME; \
  tar --extract --file /tmp/tomcat.tgz --directory $CATALINA_HOME --strip-components 1; \
  rm -rf $CATALINA_HOME/webapps/*

# Get Geoserver War file and unzip it 
RUN set -eux; \
  curl -jkSL --output /tmp/geoserver.zip http://sourceforge.net/projects/geoserver/files/GeoServer/${GEOSERVER_VERSION}/geoserver-${GEOSERVER_VERSION}-war.zip; \
  mkdir /tmp/geoserver; \
  mkdir /tmp/geoserver-war-unpacked; \
  mkdir /tmp/geoserver-gdal-plugin; \
  mkdir /tmp/geoserver-css-plugin; \
  mkdir /tmp/geoserver-excel-plugin; \
  unzip -d /tmp/geoserver/ /tmp/geoserver.zip; \
  unzip -d /tmp/geoserver-war-unpacked  /tmp/geoserver/geoserver.war; \
  # Remove default data and users. Set up to fail if files do not exist, so we catch changes in the structure in future releases
  rm -r /tmp/geoserver-war-unpacked/data/coverages; \
  rm -r /tmp/geoserver-war-unpacked/data/data; \
  rm -r /tmp/geoserver-war-unpacked/data/demo; \
  rm -r /tmp/geoserver-war-unpacked/data/layergroups; \
  rm -r /tmp/geoserver-war-unpacked/data/workspaces; \
  #rm /tmp/geoserver-war-unpacked/data/security/usergroup/default/users.xml; \
  curl -jkSL --output /tmp/geoserver-gdal-plugin.zip http://sourceforge.net/projects/geoserver/files/GeoServer/${GEOSERVER_VERSION}/extensions/geoserver-${GEOSERVER_VERSION}-gdal-plugin.zip; \
  curl -jkSL --output /tmp/geoserver-css-plugin.zip http://sourceforge.net/projects/geoserver/files/GeoServer/${GEOSERVER_VERSION}/extensions/geoserver-${GEOSERVER_VERSION}-css-plugin.zip; \
  curl -jkSL --output /tmp/geoserver-excel-plugin.zip http://sourceforge.net/projects/geoserver/files/GeoServer/${GEOSERVER_VERSION}/extensions/geoserver-${GEOSERVER_VERSION}-excel-plugin.zip; \
  curl -jkSL --output /tmp/geoserver-wps-plugin.zip http://sourceforge.net/projects/geoserver/files/GeoServer/${GEOSERVER_VERSION}/extensions/geoserver-${GEOSERVER_VERSION}-wps-plugin.zip; \
  unzip -d /tmp/geoserver-gdal-plugin/ /tmp/geoserver-gdal-plugin.zip; \
  unzip -d /tmp/geoserver-css-plugin/ /tmp/geoserver-css-plugin.zip; \
  unzip -d /tmp/geoserver-excel-plugin/ /tmp/geoserver-excel-plugin.zip; \
  unzip -d /tmp/geoserver-wps-plugin/ /tmp/geoserver-wps-plugin.zip;


# Start the actual image, and cherry-pick files from the builder:
FROM adoptopenjdk/openjdk11:jre-11.0.11_9-alpine
ENV CATALINA_HOME=/usr/local/tomcat
ENV PATH=$CATALINA_HOME/bin:$PATH

RUN apk add --no-cache ttf-dejavu java-gdal curl
RUN export GDAL_DATA=/usr/share/gdal/
RUN adduser --disabled-password --uid 1000 1000
# The Tomcat Server
COPY --chown=1000 --from=builder $CATALINA_HOME $CATALINA_HOME
# The core GeoServer
COPY --chown=1000 --from=builder /tmp/geoserver-war-unpacked /usr/local/tomcat/webapps/geoserver/
# Official plugins
COPY --chown=1000 --from=builder /tmp/geoserver-css-plugin /usr/local/tomcat/webapps/geoserver/WEB-INF/lib/
COPY --chown=1000 --from=builder /tmp/geoserver-excel-plugin /usr/local/tomcat/webapps/geoserver/WEB-INF/lib/
COPY --chown=1000 --from=builder /tmp/geoserver-gdal-plugin /usr/local/tomcat/webapps/geoserver/WEB-INF/lib/
COPY --chown=1000 --from=builder /tmp/geoserver-wps-plugin /usr/local/tomcat/webapps/geoserver/WEB-INF/lib/

COPY --chown=1000 ./image-configuration/server-9.0.40.xml /usr/local/tomcat/conf/server.xml
COPY --chown=1000 ./image-configuration/web.xml /usr/local/tomcat/webapps/geoserver/WEB-INF/web.xml
COPY --chown=1000 ./image-configuration/context.xml /usr/local/tomcat/webapps/geoserver/META-INF/context.xml

COPY --chown=1000 ./image-configuration/esk8 /usr/local/tomcat/webapps/geoserver/data/workspaces/esk8
USER 1000
EXPOSE 8081
HEALTHCHECK CMD curl --fail http://localhost:8081/web/wicket/bookmarkable/org.geoserver.web.demo.MapPreviewPage || exit 1
CMD ["catalina.sh", "run"]

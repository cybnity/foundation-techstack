# Docker launch maven build without need to have maven installed on host
# Docker builds an image from javaruntime container generated tar archive
# Docker file use a docker multi-stage build to provide a minimal image (first part of the dockerfile is just used build the Javaruntime distribution with maven)

FROM maven:3.6.0-jdk-8 as JAVARUNTIME-BUILD

RUN mkdir /src;
WORKDIR /src/
# Add source codes project to compile and/or to assemble as system
ADD . .

# Build and publish all sub-projects to AGnet remote repository (use a custom settings.xml file to publish artefacts to repositories)
RUN mvn clean install --global-settings /src/conf/settings.xml

# The final base image regarding Java Runtime (based on https://hub.docker.com/_/eclipse-temurin description)
#FROM eclipse-temurin:11-alpine
#FROM eclipse-temurin:11.0.15_10-jre-alpine
#FROM <Linux base image not including Temurin pre-installed>
FROM eclipse-temurin:11-jre-alpine

MAINTAINER Olivier Lemée

VOLUME "/root/.m2"

# Default image's Java home directory is
#ENV JAVA_HOME=/opt/java/openjdk

# Create applications directory
RUN mkdir /opt/app;

# Deploy the appplications into the image's applications directory
# Copy the CYBNITY software binaries packages ready as executable system
#COPY --from=JAVARUNTIME-BUILD /src/<module name>/target/<artefact name>-1.0-SNAPSHOT.jar /opt/app

# Build logs area
#RUN mkdir -p $KARAF_HOME/data/log && touch $KARAF_HOME/data/log/karaf.log
#RUN chmod 755 $KARAF_HOME/data/log

# Open ports
#EXPOSE 1099 8101 44444 8181

#CMD ["java", "-jar", "/opt/app/<artefact>.jar"]

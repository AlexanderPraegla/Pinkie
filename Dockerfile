FROM airhacks/glassfish:v5
LABEL maintainer="Altenerding Biber, altenerding-biber.com" description="GlassFish v5 with Altenerding Biber website"
COPY target/pinkie.war ${DEPLOYMENT_DIR}
ENV WAR pinkie.war

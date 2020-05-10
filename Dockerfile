FROM baseimage

RUN mkdir -p /usr/src/app
ADD backEnd/target/daily-follow-up-0.0.1.jar /usr/src/app/appli.jar

EXPOSE 8080
CMD [ "java", "-DCONF_DIR=/usr/src/app/conf", "-Dlogging.config=/usr/src/app/logback-spring.xml", "-jar", "/usr/src/app/appli.jar" ]
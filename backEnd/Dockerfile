FROM baseimage

RUN mkdir -p /usr/src/app
#ADD target/daily-follow-up-0.0.1.jar /usr/src/app/appli.jar
ARG jar=backEnd/target/daily-follow-up-0.0.1.jar
#ARG conffile=home/pi/Documents/Project/daily-follow-up/dailyFollowUp.properties
#ARG logfile=./home/pi/Documents/Project/daily-follow-up/logback-spring.xml


#RUN mkdir -p /usr/src/app
#COPY $conffile /usr/src/app/dailyFollowUp.properties
#COPY $logfile /usr/src/app/logback-spring.xml
RUN cp $jar /usr/src/app/appli.jar

EXPOSE 8080
CMD [ "java", "-DCONF_DIR=/usr/src/app/conf", "-Dlogging.config=/usr/src/app/logback-spring.xml", "-jar", "/usr/src/app/appli.jar" ]
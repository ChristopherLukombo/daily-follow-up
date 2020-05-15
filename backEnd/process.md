- Ce fichier détaille les manipulations à faire pour que le projet soit fonctionnel pour la prochaine personne.

15/05/2020------------------------------------TAG GIT----------------------------------------------------------------------------------

Ajouter ces variables de configuration dans le fichier de configuration afin d'exposer HEALTH ENDPOINT sur http://localhost:9000/actuator/health

# ACTUATOR
management.server.port=9000
management.endpoints.web.exposure.include=health,info,metrics
management.endpoint.shutdown.enabled=true
info.app.version=0.0.1

# HEALTH ENDPOINT
management.endpoint.health.show-details=always

02/05/2020------------------------------------TAG GIT----------------------------------------------------------------------------------
Supprimer la variable de configuration suivante dans le fichier de configuration "dailyFollowUp.properties" : 
 - spring.messages.basename=i18n/messages

# Dans le fichier "dailyFollowUp.properties", ajouter la variable de configuration suivante :

menu.imagesPath=C:\\Users\\\christopher\\\ProgramDev\\\daily-follow-up\\\images\\logo-almaviva-sante.png

puis rajouter l'image se trouvant dans src/main/resources/images, dans le chemin de la variable de configuration.
------------------------------------------------------------------------------------------------------------------------
# Dans le fichier "dailyFollowUp.properties", supprimer la variable de configuration
 "token.tokenValidityInMillisecondsForRememberMe".

# Dans le fichier "dailyFollowUp.properties", ajouter les variables de configuration suivantes :

spring.jpa.properties.hibernate.jdbc.batch_size=4
spring.jpa.properties.hibernate.order_inserts=true 
------------------------------------------------------------------------------------------------------------------------
# Dans le fichier "dailyFollowUp.properties", ajouter la variable de configuration suivante :

user.pathProfiles=C:\\Users\\christopher\\ProgramDev\\daily-follow-up\\profiles\\
------------------------------------------------------------------------------------------------------------------------
# Dans le fichier "dailyFollowUp.properties", ajouter la variable de configuration suivante :
spring.jpa.properties.hibernate.order_updates=true
------------------------------------------------------------------------------------------------------------------------


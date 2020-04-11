- Ce fichier détaille les manipulations à faire pour que le projet soit fonctionnel pour la prochaine personne.
------------------------------------------------------------------------------------------------------------------------
# Dans le fichier "dailyFollowUp.properties", supprimer la variable de configuration
 "token.tokenValidityInMillisecondsForRememberMe".

# Dans le fichier "dailyFollowUp.properties", ajouter les variables de configuration suivantes :

spring.jpa.properties.hibernate.jdbc.batch_size=4
spring.jpa.properties.hibernate.order_inserts=true 
------------------------------------------------------------------------------------------------------------------------
# Dans le fichier "dailyFollowUp.properties", ajouter la variable de configuration suivante :

user.pathProfiles=C:\\Users\\christopher\\ProgramDev\\daily-follow-up\\profiles\\

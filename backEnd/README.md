# Daily follow up

**Le projet**

Solution de suivi de commande des repas patients dans une clinique de soins de suite.
Celle-ci comporte :
- une application mobile pour le personnel soignant qui va permettre de renseigner les choix patients.
- une interface web pour le personnel dieteticien qui va permettre de décliner les menus, vérifier


**L'équipe**

Notre groupe est composé des personnes suivantes :

- Neal KEMISSSI
- Angelo DELIESSCHE
- Christopher LUKOMBO

**Prérequis :**

Mettre la variable dans VM arguments au niveau du build MAVEN dans eclipse ou Intellij

-DCONF_DIR=C:\Users\christopher\ProgramDev\conf\dailyFollowUp

Ajouter une copie du fichier de configuration "dailyFollowUp.properties" correspondant au chemin indiqué dans la variable -DCONF_DIR. Le fichier "dailyFollowUp.properties" permettra d'externaliser les variables de configuration.

Aussi, mettre également la variable dans VM arguments au niveau du build MAVEN dans eclipse ou Intellij
-Dlogging.config="file:C:\Users\Christopher LUKOMBO\ProgramDev\conf\dailyFollowUp\logback-spring.xml"

Puis, ajouter une copie du fichier logback-spring.xml dans le chemin spécifié.
Editez ensuite le fichier logback-spring.xml, remplacer la valeur de la property value par le chemin où vous voulez stocker les logs.
<property name="LOGS" value="C:\\Users\\Christopher LUKOMBO\\ProgramDev\\logs\\dailyFollowUp" />

Créer la base de données PostgreSQL "daily-follow-up"

Pour lancer la partie Angular, se placer dans le dossier frontEnd et lancer la commande ng serve --open

Swagger : http://localhost:8080/swagger-ui.html#

HEALTH CHECK : 
dev : http://localhost:9000/actuator/health
prod : http://delissch.freeboxos.fr/actuator/health

Pour activer le profile prod utiliser la variable de conf suivante : 

-Dspring.profiles.active=prod

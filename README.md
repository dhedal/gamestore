# GameStore Application

Ce projet est une application SPA utilisant Spring Boot pour le backend et Webpack pour le frontend. 
Suivez les étapes ci-dessous pour configurer et exécuter l'application en local.

## Prérequis

- [Java JDK (version 21)](https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html)
- [Maven](https://maven.apache.org/)
- [Mysql (version 8)](https://dev.mysql.com/downloads/installer/)
- [MongoDB](https://www.mongodb.com/fr-fr)
- [Node.js (version 20)](https://nodejs.org/fr/download/package-manager)
- npm
- [Git](https://git-scm.com/downloads)

## Installation et construction du projet
Afin d'installer le projet sur votre machine locale, veuillez suivre ces étapes.

1. **Cloner le dépôt** :
   ```bash
   git clone git@github.com:dhedal/gamestore.git
   cd gamestore

2. **Configurer le back-end**:
- dans le répertoire racine "gamestore", exécutez maven pour construire le projet:
   ```bash
   mvn clean install
  
3. **Configurer le front-end**:
- dans le répertoire "gamestore/frontend-config", installez les dépendances npm:
   ```bash
   npm install
- ensuite exécutez cette commande qui permet à webpack de construire l'application front-end
  ```bash
   npm run build
  
## La base de donnée MySql
Il y a deux environnements différents : l'environnement de développement et l'environnement de test avant
mise en production.

1. **en mode développement**:
    - `gamestoreDevDb` : nom de la base donnée qui sera utilisée par l'application
    - `gamestoreDevDbTest` : nom de la base donnée qui sera utilisée pour les tests unitaires
2. **en mode release**:
    - `gamestoreReleaseDb` : nom de la base donnée qui sera utilisée par l'application
    - `gamestoreReleaseDbTest` : nom de la base donnée qui sera utilisée pour les tests unitaires
3. **Example de commande sql pour la création de la base de donnée**:
   ```bash
   CREATE DATABASE IF NOT EXISTS nom_de_la_base_de_donnée CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

4. **les paramètres de connexions à la base de donnée**:
- il faut définir les paramètres username et password dans les variables d'environnements de votre OS
- exemple sur window 10, en ligne de commande:
  - `setx DB_USERNAME "votre_nom_utilisateur"`
  - `setx DB_PASSWORD "votre_mot_de_passe"`


## Lancement du serveur d'application

1. **en mode développement**:
ce mode utilise les configurations locales et est idéal pour le développement
et les tests locaux.
   ```bash
   mvn spring-boot:run -Dspring-boot.run.profiles=dev

2. **en mode release**:
ce mode est utilisé pour les tests avant le déploiement en production, 
en utilisant une base de données et des configurations de test
   ```bash
   mvn spring-boot:run -Dspring-boot.run.profiles=release
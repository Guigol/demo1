# Internal Tools API

## Technologies
- Langage: [Java 17]
- Framework: [Springboot 3.5.4] 
- Base de données: H2/MySQL (MYsQL sur H2 Console)
- Port API: [8085] (configurable)

## Quick Start

1. `docker-compose --profile mysql up -d` 

## Long Start
1- ouvrir le dossier du projet avec Intellij ou Eclipse (JDK17)
2- Démarrer le projet

**Access in 30 seconds:**
- 🗄️ **H2:** `localhost:8085/h2-console`
- 🌐 **H2Dialect:** http://localhost:8085/h2-console/
- 👤 **Credentials:** `dev / dev123`
- 📊 Afin de compléter les données des tables et éviter les erreurs, lancer 3 fois le fichier [SeeData.sql] stocké dans demo1/demo1

EXPLICATION :
Java lance déjà une distribution de données via DBInit et InternalFactory, cependant incomplète. Donc il est bon de lancer [SeeData.sql] (3 fois)

- 📊 **Database:** `internal_tools`
1. API disponible sur `http://localhost:8085`
2. S'authentifier sur `http://localhost:8085/swagger-ui/index.html`
3. **Credentials:** `dev / dev123`
4. Documentation:`http://localhost:8085/v3/api-docs`


## Configuration
- Variables d'environnement: voir -> resources/application.properties
- Configuration DB: 
    Driver Class:`org.h2.Driver`
    JDBC URL:`jdbc:h2:mem:internal_tools?createDatabaseIfNotExist=true`
    User Name:`dev`
    Password:`dev123`

## Tests  
[commande_lancement_tests] - Tests unitaires + intégration

## Architecture
- Architecture MVC
- JAVA 17/Springboot : Sécurité renforcée, polymorphisme et robustesse orienté objet. Framework complet pour les API. Logique impitoyable.
SpringBoot, développé en Java, propose de nombreuses fonctions intégrées permettant l’implémentation d’application N’tier.

La structure du projet se présente comme suit :
1. 7 packages regroupant différents objets d'une même catégorie. Comme ci-dessous :
        - controllers = contient tous les controlleurs pour contrôler les acccès aux EndPoints
        - config = les classes propres à la configuration système et sécurité de l'application
        - dtos (DataTransfertObject)= récupération et retour des champs de données
        - entities = toutes les tables de la base de données
        - factory = fabrique d'une routine X ou Y (dans le cas présent, alimentation au démarrage des tables pour la BDD)
        - repositories = passe plat entre Java et la BDD (Jpa)
        - services = tous les services à exécuter dans l'application
2. Tous les services sont distincts les uns des autres



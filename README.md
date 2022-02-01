# Campaign recommendation app

#### Created by `Spiros Tzoras`

## Table of Contents

#### [1. General Architecture](#general-architecture)

#### [2. Setup Development Environment](#setup-development-environment)

#### [3. Run the application (outside IDE)](#run-the-application-outside-ide)

#### [4. Use the application (postman)](#use-the-application-postman)

## General Architecture

| Technology | Version |
| --- | --- |
| Java | 11.x |
| Gradle | 7.x |
| Spring Boot | 2.x |

## Setup Development Environment

Clone the project from the main repository and import it to your IDE.\
Install Java 11 JDK.\
If you are using IntelliJ (Recommended IDE) just import the project.\
If you are using Eclipse you will have to install the Lombok plugin.\

### Recommended way to work with the IDE:
You don't need any specific spring profile to run the app.\
The app will start using the H2 RAM database. \
Keep in mind that, since data are not persisted, the database will be deleted on every restart.\
In order to build the app double click on: `Gradle tab->tasks->build->build`\
You can always run the project from the intelliJ's internal Spring Boot Run configuration or double click on `Gradle tab->tasks->application->bootRun`.\
By default, the app runs on [http://localhost:8080](http://localhost:8080)\

### Start with persistence in place
By setting the spring profile to `mysql` (`spring.profiles.active=mysql`) you will be able to connect to a mysql database.\
Of course, you should take care of the installation and configuration of a mysql server.\
Current configuration:\
`url=jdbc:mysql://localhost:3306/campaigns`\
`schema:campaigns`\
`username=springuser`\
`password=ThePassword`\
You may change the configuration of mysql in this file:
`src/main/resources/application-mysql.properties`

## Run the application (outside IDE)
Install Java 11 JDK.\
Install Gradle.\
Install Git.\
Navigate inside the campaign folder.\
Clone the project from Github in a local folder.\
For Windows Run in console: `./gradlew bootRun` and the app should start using H2 RAM Database.

If you like to have persistence with mysql database:\
For Windows Run: `./gradlew bootRun --args='--spring.profiles.active=mysql'`\
Of course, you should take care of the installation and configuration of a mysql server.\
Current configuration:\
`url=jdbc:mysql://localhost:3306/campaigns`\
`schema:campaigns`\
`username=springuser`\
`password=ThePassword`\
You may change the configuration of mysql in this file:
`src/main/resources/application-mysql.properties`

## Use the application (postman)
The app is exposing several endpoints in order to interact:

0. Upload a CSV file (see image to locate file button in Postman: extras/postman/whereIsChooseFileButton.jpg)\
   [localhost:8080/api/v1/campaignGroup/uploadCsv/{campaignGroupNameHere}](http://localhost:8080/api/v1/campaignGroup/uploadCsv/campaignGroupNameHere)

1. View all campaign groups (through a call to campaign group)\
   [localhost:8080/api/v1/campaignGroup/all](http://localhost:8080/api/v1/campaignGroup/all)

2. View all campaigns for a campaign group (through a call to campaign)\
   [localhost:8080/api/v1/campaign/all/{campaignGroupIdHere}](http://localhost:8080/api/v1/campaign/all/campaignGroupIdHere)

3. View latest optimisations for a campaign group (through a call to optimisation)\
   [localhost:8080/api/v1/optimisation/all/{campaignGroupIdHere}](http://localhost:8080/api/v1/optimisation/all/campaignGroupIdHere)

4. View latest recommendations for an optimisation (through a call to recommendation)\
   [localhost:8080/api/v1/optimisation/recommendations/allNotApplied/{campaignGroupIdHere}](http://localhost:8080/api/v1/optimisation/recommendations/allNotApplied/campaignGroupIdHere)

5. Apply / accept latest optimisation / recommendations to a campaign group / campaigns\
   [localhost:8080/api/v1/optimisation/recommendations/apply/{campaignGroupIdHere}](http://localhost:8080/api/v1/optimisation/recommendations/apply/campaignGroupIdHere)

There is also a Postman collection that will help you call these endpoints:\
Location: `extras/postman/postman_v2.1/Campaign.postman_collection_v2_1.json`\
You may import it to your Postman app.

Sample CSV files to use as an input you may find to folder: `extras/postman/sample_csv`


# About project
The current project was implemented by Andrei Pisponen. 
The Project is a simple implementation of the web service, written on JAVA using
Spring Boot framework. It contains code, which is capable to make simple
CRUD operations with the database objects and providing result through the REST API.

# Compiling and testing the project
There are 2 ways to run this project: using Intellij IDE or run it in virtual box 
using vagrant.

## Running in IDE
To run project in IDE, the PotgreSQL database shall be installed. After installing 
the DB, the "testdb" database shall be created with credentials "dbuser:ajfo3489fj033"

From the IDE "maven" view window, run the "specification/Lifecyckle/package" target.
Wait until it finishes creating of the Controller and model classes. 
From IDE, right click on the app/src/main/java/com/andreip/SolutionApp file and select
"run SolutionApp.main()"

When app is started, it shall be available by URL: http://localhost:8080/swagger-ui.html

## Running in virtual box
To run in virtual box, VirtualBox and Vagrant software shall be installed.

https://www.virtualbox.org/wiki/Downloads

https://www.vagrantup.com/downloads.html

To run the project in the virtual box, use the next command:

    `sh prepare_jar_files.sh`
    
    `vagrant up`
    
When app is started, it shall be available by URL: http://localhost:9080/swagger-ui.html
 


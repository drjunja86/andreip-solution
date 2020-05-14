#!/bin/bash
./mvnw clean
./mvnw package
cp app/target/app-1.0.0.jar ansible/roles/solution/files/app.jar
cp generator/libs/postgresql-42.2.12.jar ansible/roles/solution/files/postgresql-42.2.12.jar
cp generator/target/generator-1.0.0.jar ansible/roles/solution/files/generator.jar
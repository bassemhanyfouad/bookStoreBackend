#!/usr/bin/env bash

docker-compose up -d
cd ..
mvn clean appengine:run -DskipTests
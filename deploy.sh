#!/bin/bash

docker run -p 9090:8080 -e "SPRING_PROFILES_ACTIVE=$1" linkit/linkit-api

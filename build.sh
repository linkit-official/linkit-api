#!/bin/bash

./gradlew bootjar
docker build --tag linkit/linkit-api .


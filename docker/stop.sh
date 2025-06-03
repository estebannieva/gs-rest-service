#!/bin/bash

PROJECT_NAME="rest-service"

docker compose -p "$PROJECT_NAME" down -v --remove-orphans

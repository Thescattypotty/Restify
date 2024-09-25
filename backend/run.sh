#!/bin/bash

echo "Starting Config Server..."

gnome-terminal --tab --title="Config Server" -- zsh -c "mvn -pl config-server spring-boot:run; exec zsh"
sleep 1

echo "Starting Server Discovery..."

gnome-terminal --tab --title="Server Discovery" -- zsh -c "mvn -pl server-discovery spring-boot:run; exec zsh"
sleep 1

echo "Starting Gateway Service..."

gnome-terminal --tab --title="Gateway Service" -- zsh -c "mvn -pl gateway-service spring-boot:run; exec zsh"
sleep 4

echo "Starting Environment Service..."

gnome-terminal --tab --title="Environment Service" -- zsh -c "mvn -pl environment-service spring-boot:run; exec zsh"
sleep 1

echo "Starting Collection Service..."

gnome-terminal --tab --title="Collection Service" -- zsh -c "mvn -pl collection-service spring-boot:run; exec zsh"
sleep 1

echo "Starting File Storage Service..."

gnome-terminal --tab --title="File Storage Service" -- zsh -c "mvn -pl fileStorage-service spring-boot:run; exec zsh"
sleep 1

#gnome-terminal --tab --title="Request History Service" -- zsh -c "mvn -pl requestHistory-service spring-boot:run; exec zsh"

#gnome-terminal --tab --title="Request Service" -- zsh -c "mvn -pl request-service spring-boot:run; exec zsh"

echo "All microservices are now running."

wait
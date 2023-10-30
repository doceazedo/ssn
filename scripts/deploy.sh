#!/bin/bash
docker compose -f docker-compose.prod.yml up --build -d gateway warehouse warehouse-migrate &&
docker builder prune -f &&
docker compose -f docker-compose.prod.yml up --build -d website &&
docker builder prune -f  &&
docker compose -f docker-compose.prod.yml up --build -d identity-web &&
docker builder prune -f &&
docker compose -f docker-compose.prod.yml up --build -d gatekeeper-redis gatekeeper-web &&
docker builder prune -f &&
docker compose -f docker-compose.prod.yml up --build -d melonbot rcon &&
docker builder prune -f &&
docker compose -f docker-compose.prod.yml up --build -d minecraft-proxy minecraft-queue minecraft-main

#!/bin/bash
docker compose up --build -d gateway warehouse warehouse-migrate &&
docker builder prune -f &&
docker compose up --build -d website &&
docker builder prune -f  &&
docker compose up --build -d identity-web &&
docker builder prune -f &&
docker compose up --build -d gatekeeper-redis gatekeeper-web &&
docker builder prune -f &&
docker compose up --build -d minecraft-proxy minecraft-queue minecraft-main

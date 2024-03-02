#!/bin/bash
cd "$(dirname "$0")"
source ../.env

cd .. &&
$DOCKER compose -f docker-compose.prod.yml up --build -d gateway warehouse warehouse-migrate &&
$DOCKER builder prune -f &&
$DOCKER compose -f docker-compose.prod.yml up --build -d website &&
$DOCKER builder prune -f  &&
$DOCKER compose -f docker-compose.prod.yml up --build -d identity-web &&
$DOCKER builder prune -f &&
$DOCKER compose -f docker-compose.prod.yml up --build -d gatekeeper-redis gatekeeper-web &&
$DOCKER builder prune -f &&
$DOCKER compose -f docker-compose.prod.yml up --build -d melonbot rcon &&
$DOCKER builder prune -f &&
$DOCKER compose -f docker-compose.prod.yml up --build -d minecraft-proxy minecraft-queue minecraft-main &&
./scripts/discord-webhook.sh --maintenance-done

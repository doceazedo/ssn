#!/bin/bash
cd "$(dirname "$0")"
source ../.env

cd .. &&
$DOCKER system prune -af --volumes &&
$DOCKER compose -f docker-compose.prod.yml up --build -d gateway warehouse warehouse-migrate &&
$DOCKER system prune -af --volumes &&
$DOCKER compose -f docker-compose.prod.yml up --build -d website &&
$DOCKER system prune -af --volumes  &&
$DOCKER compose -f docker-compose.prod.yml up --build -d identity-web &&
$DOCKER system prune -af --volumes &&
$DOCKER compose -f docker-compose.prod.yml up --build -d gatekeeper-redis gatekeeper-web &&
$DOCKER system prune -af --volumes &&
$DOCKER compose -f docker-compose.prod.yml up --build -d melonbot &&
$DOCKER system prune -af --volumes &&
$DOCKER compose -f docker-compose.prod.yml up --build -d rcon &&
$DOCKER system prune -af --volumes &&
$DOCKER compose -f docker-compose.prod.yml up --build -d minecraft-proxy minecraft-queue minecraft-main &&
$DOCKER system prune -af --volumes &&
./scripts/discord-webhook.sh --maintenance-done

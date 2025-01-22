#!/bin/bash
cd "$(dirname "$0")"
source ../.env

cd .. &&
$DOCKER system prune -a -f &&
sleep .5 &&
$DOCKER compose -f docker-compose.prod.yml up -d gateway identity-web warehouse warehouse-migrate gatekeeper-web gatekeeper-redis website melonbot &&
sleep .5 &&
$DOCKER system prune -a -f &&
sleep .5 &&
$DOCKER compose -f docker-compose.prod.yml up -d minecraft-proxy minecraft-queue minecraft-main &&
./scripts/discord-webhook.sh --maintenance-done

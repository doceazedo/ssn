#!/bin/bash
cd "$(dirname "$0")"
source ../.env

cd .. &&
$DOCKER system prune -a -f &&
$DOCKER compose -f docker-compose.prod.yml up -d &&
./scripts/discord-webhook.sh --maintenance-done

#!/bin/bash
cd "$(dirname "$0")"
source ../.env

cd .. &&
$DOCKER compose pull &&
$DOCKER compose -f docker-compose.prod.yml up -d &&
./scripts/discord-webhook.sh --maintenance-done

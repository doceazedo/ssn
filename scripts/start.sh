#!/bin/bash
cd "$(dirname "$0")"
source ../.env

(cd .. && $DOCKER compose up -d) &&
./discord-webhook.sh --maintenance-done

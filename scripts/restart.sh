#!/bin/bash
cd "$(dirname "$0")"
source ../.env

./broadcast.sh "§c§lO servidor vai reiniciar em §4§l60 segundos§c§l..." &&
sleep 30 &&
./broadcast.sh "§c§lO servidor vai reiniciar em §4§l30 segundos§c§l..." &&
sleep 10 &&
./broadcast.sh "§c§lO servidor vai reiniciar em §4§l20 segundos§c§l..." &&
sleep 10 &&
./broadcast.sh "§c§lO servidor vai reiniciar em §4§l10 segundos§c§l..." &&
sleep 5 &&
./broadcast.sh "§c§lO servidor vai reiniciar em §4§l5 segundos§c§l..." &&
sleep 5 &&
./discord-webhook.sh --daily-restart &&
./kick-all.sh "§cO servidor está sendo reiniciado" &&
(cd .. && $DOCKER compose down) &&
(cd .. && $DOCKER builder prune -f) &&
(cd .. && $DOCKER compose -f docker-compose.prod.yml up -d) &&
sleep 10 && # spigot server starting might take a while
./discord-webhook.sh --maintenance-done

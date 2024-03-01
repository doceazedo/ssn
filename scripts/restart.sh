#!/bin/bash
npm run broadcast -- "§c§lO servidor vai reiniciar em §4§l30 segundos§c§l..." &&
sleep 10 &&
npm run broadcast -- "§c§lO servidor vai reiniciar em §4§l20 segundos§c§l..." &&
sleep 10 &&
npm run broadcast -- "§c§lO servidor vai reiniciar em §4§l10 segundos§c§l..." &&
sleep 5 &&
npm run broadcast -- "§c§lO servidor vai reiniciar em §4§l5 segundos§c§l..." &&
sleep 4 &&
npm run discord-webhook -- --daily-restart &&
npm run kick-all -- "§cO servidor está sendo reiniciado" &&
docker compose down &&
docker builder prune -f &&
docker compose up -f docker-compose.prod.yml -d &&
npm run discord-webhook -- --maintenance-done

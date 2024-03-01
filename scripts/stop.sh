#!/bin/bash
npm run broadcast -- "§c§lO servidor vai entrar em manutenção em §4§l30 segundos§c§l..." &&
sleep 10 &&
npm run broadcast -- "§c§lO servidor vai entrar em manutenção em §4§l20 segundos§c§l..." &&
sleep 10 &&
npm run broadcast -- "§c§lO servidor vai entrar em manutenção em §4§l10 segundos§c§l..." &&
sleep 5 &&
npm run broadcast -- "§c§lO servidor vai entrar em manutenção em §4§l5 segundos§c§l..." &&
sleep 4 &&
npm run discord-webhook -- --maintenance &&
npm run kick-all -- "§cO servidor está em manutenção" &&
docker compose down

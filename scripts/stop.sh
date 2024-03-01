#!/bin/bash
cd "$(dirname "$0")"
source ../.env

./broadcast.sh "§c§lO servidor vai entrar em manutenção em §4§l30 segundos§c§l..." &&
sleep 10 &&
./broadcast.sh "§c§lO servidor vai entrar em manutenção em §4§l20 segundos§c§l..." &&
sleep 10 &&
./broadcast.sh "§c§lO servidor vai entrar em manutenção em §4§l10 segundos§c§l..." &&
sleep 5 &&
./broadcast.sh "§c§lO servidor vai entrar em manutenção em §4§l5 segundos§c§l..." &&
sleep 4 &&
./discord-webhook.sh -- --maintenance &&
./kick-all.sh -- "§cO servidor está em manutenção" &&
(cd .. && $DOCKER compose down)

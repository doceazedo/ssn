#!/bin/bash
docker compose up -f docker-compose.prod.yml -d &&
npm run discord-webhook -- --maintenance-done

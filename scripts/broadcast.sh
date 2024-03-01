#!/bin/bash
cd "$(dirname "$0")"
source ../.env

if [[ -z $1 ]]; then
  echo "No message informed"
  exit 1
fi

curl -X POST \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $COMMANDER_TOKEN" \
  -d "{\"message\": \"$1\"}" \
  http://localhost:25574/api/private/broadcast

if [[ $? -ne 0 ]]; then
  echo "Could not broadcast message"
  exit 1
fi

echo "Broadcast sent"

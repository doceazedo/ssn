#!/bin/bash
cd "$(dirname "$0")"
source ../.env

if [[ -z $1 ]]; then
  echo "No reason informed"
  exit 1
fi

curl -X POST \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $COMMANDER_TOKEN" \
  -d "{\"reason\": \"$1\"}" \
  http://localhost:25574/api/private/kick-all

if [[ $? -ne 0 ]]; then
  echo "Could not kick players"
  exit 1
fi

echo "All players were kicked"

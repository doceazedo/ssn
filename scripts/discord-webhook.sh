#!/bin/bash
cd "$(dirname "$0")"
source ../.env

valid_args=(
  "--daily-restart"  "\"title\": \"🚧 Manutenção\", \"description\": \"Reiniciando o servidor para manutenção diária automática, segura aí...\", \"color\": 16498468"
  "--maintenance"    "\"title\": \"🚧 Manutenção\", \"description\": \"O servidor foi fechado para manutenção.\", \"color\": 16498468"
  "--maintenance-done" "\"title\": \"✅ Servidor online\", \"description\": \"Manutenção finalizada e servidor reaberto. Bom jogo!\", \"color\": 4769678"
)

get_message_data() {
  local arg="$1"
  for (( i = 0; i < ${#valid_args[*]}; ++ i ))
  do
    local key=${valid_args[$i]}
    if [[ "$key" =~ ^"$arg" ]]; then
      echo "${valid_args[$i+1]}"
      return 0
    fi
  done
  return 1
}

# Check if a valid argument is provided
if [[ -z $1 ]]; then
  echo "No arg used"
  exit 1
fi

message_data="$(get_message_data "$1")"
if [[ $? -ne 0 ]]; then
  echo "Invalid arg"
  exit 1
fi

echo "{\"embeds\": [{$message_data}]}"

curl -X POST \
  -H "Content-Type: application/json" \
  -d "{\"embeds\": [{$message_data}]}" \
  "$DISCORD_WEBHOOK_CRONJOB"

if [[ $? -ne 0 ]]; then
  echo "Could not send webhook"
  exit 1
fi

echo "Webhook sent"

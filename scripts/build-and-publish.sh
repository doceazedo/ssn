#!/bin/bash
export NODE_ENV="production"

services=(
  "web"
  "melonbot"
)

for image in "${services[@]}"; do
  docker-compose build "$image"
  docker tag "ssn-$image" "doceazedo/ssn-$image:latest"
  docker push "doceazedo/ssn-$image:latest"
  docker builder prune -f

  echo "Processed image: $image"
done

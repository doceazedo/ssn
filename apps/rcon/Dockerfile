FROM node:18-slim
RUN apt-get update \
 && apt-get upgrade -y \
 && apt-get install -y \
      openssl

WORKDIR /app
COPY . .
RUN npm install

WORKDIR ./apps/rcon
CMD ["npm", "run", "start"]
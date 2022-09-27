FROM node:18-slim
RUN apt-get update
RUN apt-get install -y openssl

WORKDIR /app
COPY . .
RUN npm install

WORKDIR ./apps/gatekeeper
RUN npm run build
CMD ["node", "build"]
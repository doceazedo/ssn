FROM node:16-alpine

WORKDIR /app
COPY . .
RUN npm install

WORKDIR ./apps/identity
RUN npm run build
CMD ["node", "build"]
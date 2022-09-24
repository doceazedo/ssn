FROM node:18-slim
RUN apt-get update
RUN apt-get install -y openssl
WORKDIR /app
COPY . .
RUN yarn global add prisma
ENV DATABASE_URL ${DATABASE_URL}
CMD ["prisma", "migrate", "deploy"]

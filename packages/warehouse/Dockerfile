FROM node:18-slim
RUN apt-get update \
 && apt-get upgrade -y \
 && apt-get install -y \
      openssl
WORKDIR /app
COPY . .
RUN yarn global add prisma
RUN npx prisma generate
ENV DATABASE_URL ${DATABASE_URL}
CMD ["prisma", "migrate", "deploy"]

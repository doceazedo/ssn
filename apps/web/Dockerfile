FROM node:22-slim AS base
 
FROM base AS builder
RUN apt-get update \
 && apt-get upgrade -y \
 && apt-get install -y \
      openssl
# Set working directory
WORKDIR /app

RUN npm install -g npm@11.0.0
RUN npm install -g turbo
COPY . .
RUN turbo prune --scope=web --docker
 
# Add lockfile and package.json's of isolated subworkspace
FROM base AS installer
RUN apt-get update \
 && apt-get upgrade -y \
 && apt-get install -y \
      openssl
WORKDIR /app
 
# First install the dependencies (as they change less often)
COPY .gitignore .gitignore
COPY --from=builder /app/out/json/ .
COPY --from=builder /app/out/package-lock.json ./package-lock.json
RUN npm install --legacy-peer-deps
 
# Build the project
COPY --from=builder /app/out/full/ .
RUN npx turbo run build --filter=web...
 
FROM base AS production
RUN apt-get update \
 && apt-get upgrade -y \
 && apt-get install -y \
      openssl
WORKDIR /app
 
# Don't run production as root
RUN addgroup --system --gid 1001 nodejs
RUN adduser --system --uid 1001 sveltekit
USER sveltekit

COPY --from=installer /app/apps/web/package.json .
COPY --from=installer /app/node_modules node_modules
COPY --from=installer --chown=sveltekit:nodejs /app/apps/web/build ./apps/web/build

CMD node apps/web/build

FROM base AS development
RUN apt-get update \
 && apt-get upgrade -y \
 && apt-get install -y \
      openssl
WORKDIR /app
COPY --from=builder /app/out/json/ .
COPY --from=builder /app/out/package-lock.json ./package-lock.json
RUN npm install --legacy-peer-deps
COPY ./packages ./packages
COPY ./apps/web ./apps/web
WORKDIR /app/apps/web
CMD npm run dev -- --host 0.0.0.0 --port 3000

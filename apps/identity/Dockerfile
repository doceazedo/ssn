FROM node:18-slim AS base
 
FROM base AS builder
RUN apt-get update \
 && apt-get upgrade -y \
 && apt-get install -y \
      openssl
# Set working directory
WORKDIR /app
RUN npm install -g turbo
COPY . .
RUN turbo prune --scope=identity --docker
 
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
RUN npm run warehouse:generate
RUN npx turbo run build --filter=identity...
 
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

COPY --from=installer /app/apps/identity/package.json .
COPY --from=installer /app/node_modules node_modules
COPY --from=installer --chown=sveltekit:nodejs /app/apps/identity/build ./apps/identity/build

CMD node apps/identity/build

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
COPY ./apps/identity ./apps/identity
RUN npm run warehouse:generate
WORKDIR /app/apps/identity
CMD npm run dev

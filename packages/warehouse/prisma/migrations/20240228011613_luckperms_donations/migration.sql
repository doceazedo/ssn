-- CreateEnum
CREATE TYPE "PaymentGateway" AS ENUM ('MERCADO_PAGO');

-- CreateTable
CREATE TABLE "Donation" (
    "id" TEXT NOT NULL,
    "createdAt" TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "expiresAt" TIMESTAMP(3) NOT NULL,
    "amount" INTEGER NOT NULL,
    "paymentId" TEXT NOT NULL,
    "gateway" "PaymentGateway" NOT NULL,
    "ownerName" TEXT NOT NULL,

    CONSTRAINT "Donation_pkey" PRIMARY KEY ("id")
);

-- CreateTable
CREATE TABLE "luckperms_user_permissions" (
    "id" SERIAL NOT NULL,
    "uuid" VARCHAR(36) NOT NULL,
    "permission" VARCHAR(200) NOT NULL,
    "value" BOOLEAN NOT NULL,
    "server" VARCHAR(36) NOT NULL,
    "world" VARCHAR(64) NOT NULL,
    "expiry" INTEGER NOT NULL,
    "contexts" VARCHAR(200) NOT NULL,

    CONSTRAINT "luckperms_user_permissions_pkey" PRIMARY KEY ("id")
);

-- CreateTable
CREATE TABLE "luckperms_group_permissions" (
    "id" SERIAL NOT NULL,
    "name" VARCHAR(36) NOT NULL,
    "permission" VARCHAR(200) NOT NULL,
    "value" BOOLEAN NOT NULL,
    "server" VARCHAR(36) NOT NULL,
    "world" VARCHAR(64) NOT NULL,
    "expiry" INTEGER NOT NULL,
    "contexts" VARCHAR(200) NOT NULL,

    CONSTRAINT "luckperms_group_permissions_pkey" PRIMARY KEY ("id")
);

-- CreateTable
CREATE TABLE "luckperms_players" (
    "uuid" VARCHAR(36) NOT NULL,
    "username" VARCHAR(16) NOT NULL,
    "primary_group" VARCHAR(36) NOT NULL,

    CONSTRAINT "luckperms_players_pkey" PRIMARY KEY ("uuid")
);

-- CreateTable
CREATE TABLE "luckperms_groups" (
    "name" VARCHAR(36) NOT NULL,

    CONSTRAINT "luckperms_groups_pkey" PRIMARY KEY ("name")
);

-- CreateTable
CREATE TABLE "luckperms_actions" (
    "id" SERIAL NOT NULL,
    "time" INTEGER NOT NULL,
    "actor_uuid" VARCHAR(36) NOT NULL,
    "actor_name" VARCHAR(100) NOT NULL,
    "type" CHAR(1) NOT NULL,
    "acted_uuid" VARCHAR(36) NOT NULL,
    "acted_name" VARCHAR(36) NOT NULL,
    "action" VARCHAR(300) NOT NULL,

    CONSTRAINT "luckperms_actions_pkey" PRIMARY KEY ("id")
);

-- CreateTable
CREATE TABLE "luckperms_tracks" (
    "name" VARCHAR(36) NOT NULL,
    "groups" TEXT NOT NULL,

    CONSTRAINT "luckperms_tracks_pkey" PRIMARY KEY ("name")
);

-- CreateIndex
CREATE UNIQUE INDEX "Donation_id_key" ON "Donation"("id");

-- CreateIndex
CREATE INDEX "luckperms_user_permissions_uuid" ON "luckperms_user_permissions"("uuid");

-- CreateIndex
CREATE INDEX "luckperms_group_permissions_name" ON "luckperms_group_permissions"("name");

-- CreateIndex
CREATE INDEX "luckperms_players_username" ON "luckperms_players"("username");

-- AddForeignKey
ALTER TABLE "Donation" ADD CONSTRAINT "Donation_ownerName_fkey" FOREIGN KEY ("ownerName") REFERENCES "Username"("name") ON DELETE RESTRICT ON UPDATE CASCADE;

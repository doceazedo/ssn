-- CreateEnum
CREATE TYPE "Service" AS ENUM ('DISCORD');

-- AlterTable
ALTER TABLE "Identity" ALTER COLUMN "email" DROP NOT NULL,
ALTER COLUMN "password" DROP NOT NULL;

-- CreateTable
CREATE TABLE "Connection" (
    "id" TEXT NOT NULL,
    "service" "Service" NOT NULL,
    "accessToken" TEXT NOT NULL,
    "expiresAt" TIMESTAMP(3) NOT NULL,
    "refreshToken" TEXT NOT NULL,
    "scope" TEXT NOT NULL,
    "tokenType" TEXT NOT NULL,
    "ownerId" TEXT NOT NULL,

    CONSTRAINT "Connection_pkey" PRIMARY KEY ("id")
);

-- CreateIndex
CREATE UNIQUE INDEX "Connection_id_key" ON "Connection"("id");

-- AddForeignKey
ALTER TABLE "Connection" ADD CONSTRAINT "Connection_ownerId_fkey" FOREIGN KEY ("ownerId") REFERENCES "Identity"("uuid") ON DELETE RESTRICT ON UPDATE CASCADE;

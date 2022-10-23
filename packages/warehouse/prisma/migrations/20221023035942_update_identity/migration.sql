-- CreateEnum
CREATE TYPE "Role" AS ENUM ('USER', 'ADMIN');

-- AlterTable
ALTER TABLE "Identity" ADD COLUMN     "createdAt" TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
ADD COLUMN     "isBanned" BOOLEAN NOT NULL DEFAULT false,
ADD COLUMN     "role" "Role" NOT NULL DEFAULT 'USER';

-- AlterTable
ALTER TABLE "Username" ADD COLUMN     "createdAt" TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
ADD COLUMN     "firstJoin" TIMESTAMP(3),
ADD COLUMN     "lastSeen" TIMESTAMP(3);

-- CreateTable
CREATE TABLE "Badge" (
    "slug" TEXT NOT NULL,
    "label" TEXT NOT NULL,

    CONSTRAINT "Badge_pkey" PRIMARY KEY ("slug")
);

-- CreateTable
CREATE TABLE "IdentityBadges" (
    "ownerId" TEXT NOT NULL,
    "badgeSlug" TEXT NOT NULL,

    CONSTRAINT "IdentityBadges_pkey" PRIMARY KEY ("ownerId","badgeSlug")
);

-- CreateIndex
CREATE UNIQUE INDEX "Badge_slug_key" ON "Badge"("slug");

-- AddForeignKey
ALTER TABLE "IdentityBadges" ADD CONSTRAINT "IdentityBadges_ownerId_fkey" FOREIGN KEY ("ownerId") REFERENCES "Identity"("uuid") ON DELETE RESTRICT ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE "IdentityBadges" ADD CONSTRAINT "IdentityBadges_badgeSlug_fkey" FOREIGN KEY ("badgeSlug") REFERENCES "Badge"("slug") ON DELETE RESTRICT ON UPDATE CASCADE;

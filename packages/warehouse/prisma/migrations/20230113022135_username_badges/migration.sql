/*
  Warnings:

  - You are about to drop the `IdentityBadges` table. If the table is not empty, all the data it contains will be lost.

*/
-- DropForeignKey
ALTER TABLE "IdentityBadges" DROP CONSTRAINT "IdentityBadges_badgeSlug_fkey";

-- DropForeignKey
ALTER TABLE "IdentityBadges" DROP CONSTRAINT "IdentityBadges_ownerId_fkey";

-- DropTable
DROP TABLE "IdentityBadges";

-- CreateTable
CREATE TABLE "UsernameBadges" (
    "ownerName" TEXT NOT NULL,
    "badgeSlug" TEXT NOT NULL,

    CONSTRAINT "UsernameBadges_pkey" PRIMARY KEY ("ownerName","badgeSlug")
);

-- AddForeignKey
ALTER TABLE "UsernameBadges" ADD CONSTRAINT "UsernameBadges_ownerName_fkey" FOREIGN KEY ("ownerName") REFERENCES "Username"("name") ON DELETE RESTRICT ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE "UsernameBadges" ADD CONSTRAINT "UsernameBadges_badgeSlug_fkey" FOREIGN KEY ("badgeSlug") REFERENCES "Badge"("slug") ON DELETE RESTRICT ON UPDATE CASCADE;

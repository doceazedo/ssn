/*
  Warnings:

  - You are about to drop the `ProfileLikes` table. If the table is not empty, all the data it contains will be lost.

*/
-- DropForeignKey
ALTER TABLE "ProfileLikes" DROP CONSTRAINT "ProfileLikes_likerId_fkey";

-- DropForeignKey
ALTER TABLE "ProfileLikes" DROP CONSTRAINT "ProfileLikes_profileId_fkey";

-- DropTable
DROP TABLE "ProfileLikes";

-- CreateTable
CREATE TABLE "ProfileLike" (
    "id" SERIAL NOT NULL,
    "likerId" TEXT NOT NULL,
    "profileId" TEXT NOT NULL,

    CONSTRAINT "ProfileLike_pkey" PRIMARY KEY ("id")
);

-- AddForeignKey
ALTER TABLE "ProfileLike" ADD CONSTRAINT "ProfileLike_likerId_fkey" FOREIGN KEY ("likerId") REFERENCES "Identity"("uuid") ON DELETE RESTRICT ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE "ProfileLike" ADD CONSTRAINT "ProfileLike_profileId_fkey" FOREIGN KEY ("profileId") REFERENCES "Profile"("uuid") ON DELETE RESTRICT ON UPDATE CASCADE;

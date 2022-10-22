/*
  Warnings:

  - You are about to drop the column `slug` on the `Invite` table. All the data in the column will be lost.

*/
-- DropIndex
DROP INDEX "Invite_slug_key";

-- AlterTable
ALTER TABLE "Invite" DROP COLUMN "slug";

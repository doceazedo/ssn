/*
  Warnings:

  - A unique constraint covering the columns `[slug]` on the table `Invite` will be added. If there are existing duplicate values, this will fail.
  - Added the required column `slug` to the `Invite` table without a default value. This is not possible if the table is not empty.

*/
-- AlterTable
ALTER TABLE "Invite" ADD COLUMN     "slug" TEXT NOT NULL;

-- CreateIndex
CREATE UNIQUE INDEX "Invite_slug_key" ON "Invite"("slug");

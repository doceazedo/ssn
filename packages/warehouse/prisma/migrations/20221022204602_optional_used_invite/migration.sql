-- DropForeignKey
ALTER TABLE "Invite" DROP CONSTRAINT "Invite_usedById_fkey";

-- AlterTable
ALTER TABLE "Invite" ALTER COLUMN "usedById" DROP NOT NULL;

-- AddForeignKey
ALTER TABLE "Invite" ADD CONSTRAINT "Invite_usedById_fkey" FOREIGN KEY ("usedById") REFERENCES "Identity"("uuid") ON DELETE SET NULL ON UPDATE CASCADE;

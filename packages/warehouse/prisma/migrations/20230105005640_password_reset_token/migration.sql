-- AlterTable
ALTER TABLE "Identity" ADD COLUMN     "passwordResetToken" TEXT,
ADD COLUMN     "passwordResetTokenCreatedAt" TIMESTAMP(3);

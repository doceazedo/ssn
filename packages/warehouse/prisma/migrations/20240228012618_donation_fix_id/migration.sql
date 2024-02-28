/*
  Warnings:

  - The primary key for the `Donation` table will be changed. If it partially fails, the table could be left without primary key constraint.
  - The `id` column on the `Donation` table would be dropped and recreated. This will lead to data loss if there is data in the column.

*/
-- DropIndex
DROP INDEX "Donation_id_key";

-- AlterTable
ALTER TABLE "Donation" DROP CONSTRAINT "Donation_pkey",
DROP COLUMN "id",
ADD COLUMN     "id" SERIAL NOT NULL,
ADD CONSTRAINT "Donation_pkey" PRIMARY KEY ("id");

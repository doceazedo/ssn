-- AlterTable
ALTER TABLE "luckperms_actions" ALTER COLUMN "time" SET DATA TYPE BIGINT;

-- AlterTable
ALTER TABLE "luckperms_group_permissions" ALTER COLUMN "expiry" SET DATA TYPE BIGINT;

-- AlterTable
ALTER TABLE "luckperms_user_permissions" ALTER COLUMN "expiry" SET DATA TYPE BIGINT;

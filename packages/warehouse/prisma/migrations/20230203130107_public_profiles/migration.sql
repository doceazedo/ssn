-- AlterTable
ALTER TABLE "Connection" ADD COLUMN     "isPublic" BOOLEAN NOT NULL DEFAULT false;

-- CreateTable
CREATE TABLE "Profile" (
    "uuid" TEXT NOT NULL,
    "ownerName" TEXT NOT NULL,
    "aboutMe" TEXT,
    "showAlts" BOOLEAN NOT NULL DEFAULT false,

    CONSTRAINT "Profile_pkey" PRIMARY KEY ("uuid")
);

-- CreateTable
CREATE TABLE "ProfileLikes" (
    "id" SERIAL NOT NULL,
    "likerId" TEXT NOT NULL,
    "profileId" TEXT NOT NULL,

    CONSTRAINT "ProfileLikes_pkey" PRIMARY KEY ("id")
);

-- CreateIndex
CREATE UNIQUE INDEX "Profile_uuid_key" ON "Profile"("uuid");

-- CreateIndex
CREATE UNIQUE INDEX "Profile_ownerName_key" ON "Profile"("ownerName");

-- AddForeignKey
ALTER TABLE "Profile" ADD CONSTRAINT "Profile_ownerName_fkey" FOREIGN KEY ("ownerName") REFERENCES "Username"("name") ON DELETE RESTRICT ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE "ProfileLikes" ADD CONSTRAINT "ProfileLikes_likerId_fkey" FOREIGN KEY ("likerId") REFERENCES "Identity"("uuid") ON DELETE RESTRICT ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE "ProfileLikes" ADD CONSTRAINT "ProfileLikes_profileId_fkey" FOREIGN KEY ("profileId") REFERENCES "Profile"("uuid") ON DELETE RESTRICT ON UPDATE CASCADE;

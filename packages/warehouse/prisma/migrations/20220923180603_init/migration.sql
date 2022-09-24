-- CreateTable
CREATE TABLE "Identity" (
    "uuid" TEXT NOT NULL,
    "email" TEXT NOT NULL,
    "password" TEXT NOT NULL,
    "verified" BOOLEAN NOT NULL DEFAULT false,
    "token" TEXT,
    "primaryUsername" TEXT NOT NULL,

    CONSTRAINT "Identity_pkey" PRIMARY KEY ("uuid")
);

-- CreateTable
CREATE TABLE "Username" (
    "name" TEXT NOT NULL,
    "ownerId" TEXT NOT NULL,

    CONSTRAINT "Username_pkey" PRIMARY KEY ("name")
);

-- CreateTable
CREATE TABLE "Invite" (
    "code" TEXT NOT NULL,
    "ownerId" TEXT NOT NULL,
    "usedById" TEXT NOT NULL,

    CONSTRAINT "Invite_pkey" PRIMARY KEY ("code")
);

-- CreateIndex
CREATE UNIQUE INDEX "Identity_uuid_key" ON "Identity"("uuid");

-- CreateIndex
CREATE UNIQUE INDEX "Identity_email_key" ON "Identity"("email");

-- CreateIndex
CREATE UNIQUE INDEX "Identity_token_key" ON "Identity"("token");

-- CreateIndex
CREATE UNIQUE INDEX "Identity_primaryUsername_key" ON "Identity"("primaryUsername");

-- CreateIndex
CREATE UNIQUE INDEX "Username_name_key" ON "Username"("name");

-- CreateIndex
CREATE UNIQUE INDEX "Invite_code_key" ON "Invite"("code");

-- CreateIndex
CREATE UNIQUE INDEX "Invite_usedById_key" ON "Invite"("usedById");

-- AddForeignKey
ALTER TABLE "Username" ADD CONSTRAINT "Username_ownerId_fkey" FOREIGN KEY ("ownerId") REFERENCES "Identity"("uuid") ON DELETE RESTRICT ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE "Invite" ADD CONSTRAINT "Invite_ownerId_fkey" FOREIGN KEY ("ownerId") REFERENCES "Identity"("uuid") ON DELETE RESTRICT ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE "Invite" ADD CONSTRAINT "Invite_usedById_fkey" FOREIGN KEY ("usedById") REFERENCES "Identity"("uuid") ON DELETE RESTRICT ON UPDATE CASCADE;

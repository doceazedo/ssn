import { Donation } from "@prisma/client";
import { prisma } from ".";

export const getUserDonations = async (user: string) =>
  await prisma.donation.findMany({
    where: {
      ownerName: user,
    },
    orderBy: {
      createdAt: "desc",
    },
  });

const createDonation = async (
  data: Omit<Donation, "id" | "createdAt">
): Promise<Donation | null> =>
  await prisma.donation.create({
    data,
  });

export const addDonation = async (
  data: Omit<Donation, "id" | "createdAt" | "expiresAt">,
  days: number
) => {
  const donations = await getUserDonations(data.ownerName);
  const unexpiredDonation = donations.find(
    (donation) => donation.expiresAt.getTime() > Date.now()
  );
  const expiresAt = unexpiredDonation?.expiresAt || new Date();
  expiresAt.setDate(expiresAt.getDate() + days);
  console.log("Creating donation...");
  return await createDonation({
    ...data,
    expiresAt,
  });
};

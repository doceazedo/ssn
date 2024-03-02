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

export const filterDonators = async (users: string[]) => {
  const donations = await prisma.donation.findMany({
    where: {
      OR: users.map((user) => ({ ownerName: user })),
      expiresAt: {
        gt: new Date(),
      },
    },
  });
  return Array.from(new Set(donations.map((x) => x.ownerName)));
};

export const getDonatorStatus = async (user: string) => {
  const donations = await prisma.donation.findMany({
    where: {
      ownerName: user,
      expiresAt: {
        gt: new Date(),
      },
    },
  });
  return donations.length > 0;
};

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
  // TODO: this could use `where: { expiresAt: { gt: new Date() } }`
  const donations = await getUserDonations(data.ownerName);
  const unexpiredDonation = donations.find(
    (donation) => donation.expiresAt.getTime() > Date.now()
  );
  const expiresAt = unexpiredDonation?.expiresAt || new Date();
  expiresAt.setDate(expiresAt.getDate() + days);
  return await createDonation({
    ...data,
    expiresAt,
  });
};

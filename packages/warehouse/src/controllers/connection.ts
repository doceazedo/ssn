import { Connection } from "@prisma/client";
import { prisma } from ".";

export const getConnection = async (id: string): Promise<Connection | null> =>
  await prisma.connection.findUnique({
    where: {
      id
    }
  });

export const getUserConnections = async (ownerId: string): Promise<Connection[]> =>
  await prisma.connection.findMany({
    where: {
      ownerId
    }
  });

export const createConnection = async (data: Connection): Promise<Connection | null> =>
  await prisma.connection.create({
    data
  });

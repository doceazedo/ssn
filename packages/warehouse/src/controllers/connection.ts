import { Connection, Service } from "@prisma/client";
import { prisma } from ".";

export const getConnection = async (id: string): Promise<Connection | null> =>
  await prisma.connection.findUnique({
    where: {
      id,
    },
  });

export const getUserConnection = async (ownerId: string, service: Service) =>
  await prisma.connection.findFirst({
    where: {
      ownerId,
      service,
    },
  });

export const getUserConnections = async (
  ownerId: string,
  publicOnly = false
): Promise<Connection[]> =>
  await prisma.connection.findMany({
    where: {
      ownerId,
      isPublic: publicOnly || undefined,
    },
  });

export const createConnection = async (
  data: Connection
): Promise<Connection | null> =>
  await prisma.connection.create({
    data,
  });

export const updateConnection = async (
  id: string,
  data: Partial<Connection>
): Promise<Connection | null> =>
  await prisma.connection.update({
    where: {
      id,
    },
    data,
  });

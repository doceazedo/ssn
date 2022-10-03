import { v4 as uuidv4 } from 'uuid';
import { connectDatabase, redis } from ".";

type GrantData = {
  ownerUuid: string;
  username: string,
  ip: string,
  authorized: boolean,
  allUsernames: boolean,
};

type Grant = {
  uuid: string;
} & GrantData;

export const validGrantTTL = [-1, 5, 86400];

export const getGrantByOwnerIp = async (owner: string, ip: string): Promise<Grant | null> => {
  throw Error('TODO: Implement getGrantByOwnerIp');
}

export const createGrant = async (data: GrantData, ttl: number): Promise<Grant | null> => {
  await connectDatabase();
  const uuid = uuidv4();
  const result = await redis.setEx(`grant:${uuid}`, ttl, JSON.stringify(data));
  if (result !== 'OK') return null;
  return { uuid, ...data };
}

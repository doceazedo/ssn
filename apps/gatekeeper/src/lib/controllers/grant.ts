import { connectDatabase, redis } from ".";

type GrantData = {
  ownerUuid: string;
  ip: string;
  username: string;
  authorized: boolean;
};

type Grant = {
  key: string;
} & GrantData;

export const validGrantTTL = [-1, 5, 86400];

export const getGrant = async (ownerUuid: string, ip: string, username: string): Promise<Grant | null> => {
  await connectDatabase();
  const key = `grants:${ownerUuid}:${ip}:${username}`;
  const grant = await redis.get(key);
  if (!grant) return null;
  return {
    key,
    ownerUuid,
    ip,
    username,
    authorized: grant === 'true'
  };
}

export const isGranted = async (ownerUuid: string, ip: string, username: string): Promise<Grant | null> => {
  const usernameGrant = getGrant(ownerUuid, ip, username);
  if (usernameGrant) return usernameGrant;
  return getGrant(ownerUuid, ip, '*');
}

export const setGrant = async (data: GrantData, ttl: number): Promise<Grant | null> => {
  await connectDatabase();
  const key = `grants:${data.ownerUuid}:${data.ip}:${data.username}`;
  const result = await redis.setEx(key, ttl, data.authorized.toString());
  if (result !== 'OK') return null;
  return { key, ...data };
}

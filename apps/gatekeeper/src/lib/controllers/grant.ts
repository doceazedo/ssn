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
  const usernameGrant = getGrant(ownerUuid, ip, username.toLowerCase());
  if (usernameGrant) return usernameGrant;
  return getGrant(ownerUuid, ip, '*');
}

export const setGrant = async (data: GrantData, ttl: number): Promise<Grant | null> => {
  await connectDatabase();
  const key = `grants:${data.ownerUuid}:${data.ip}:${data.username.toLowerCase()}`;
  const value = data.authorized.toString();
  try {
    const result = ttl >= 0 ? await redis.setEx(key, ttl, value) : await redis.set(key, value);
    if (result !== 'OK') return null;
  } catch (error) {
    console.log(error);
    return null;
  }
  return { key, ...data };
}

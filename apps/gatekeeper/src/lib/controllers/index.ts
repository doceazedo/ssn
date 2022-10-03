import { createClient } from 'redis';
import { env } from "$env/dynamic/private";

export const redis = createClient({
  url: env.GK_REDIS_URL
});

export const connectDatabase = async () => {
  if (redis.isOpen) return;
  await redis.connect();
};

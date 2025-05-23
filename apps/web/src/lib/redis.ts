import { env } from '$env/dynamic/private';
import { createClient, type RedisClientType, type RedisDefaultModules } from 'redis';

// eslint-disable-next-line @typescript-eslint/no-explicit-any
let redis: RedisClientType<RedisDefaultModules, Record<string, any>, Record<string, any>> | null =
	null;

export const getRedisClient = async () => {
	if (redis) return redis;
	redis = await createClient({
		url: env.GK_REDIS_URL
	})
		.on('error', (err) => console.log('Redis Client Error', err))
		.connect();
	return redis;
};

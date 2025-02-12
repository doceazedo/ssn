import { env } from '$env/dynamic/public';
import PocketBase from 'pocketbase';

export const pb = new PocketBase(env.PUBLIC_POCKETBASE_URL);
// pb.autoCancellation(false);

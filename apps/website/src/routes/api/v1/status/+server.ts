import { status as getServerStatus } from 'minecraft-server-util';
import { json } from "@sveltejs/kit";
import type { RequestHandler } from '@sveltejs/kit';

const ip = 'ssn.gg';

export const GET: RequestHandler = async () => {
  const status = await getServerStatus(ip);

  return json({
    status
  });
};

import { status as getServerStatus, type JavaStatusResponse } from 'minecraft-server-util';
import { json } from "@sveltejs/kit";
import type { RequestHandler } from '@sveltejs/kit';

const ip = 'ssn.gg';

export const GET: RequestHandler = async () => {
  let status: JavaStatusResponse | null = null;
  try {
    status = await getServerStatus(ip);
  } catch (e) { 
    // you'll never see me ;)
  }

  return json({
    status
  });
};

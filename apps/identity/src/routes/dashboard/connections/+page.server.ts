import type { PageServerLoad } from './$types';
import { getUserConnections } from "warehouse";

export const load: PageServerLoad = async ({ locals }) => {
  const { identity } = locals;
  if (!identity) return {};
  const connections = await getUserConnections(identity.uuid);
  return {
    services: connections.map(connection => connection.service)
  };
}

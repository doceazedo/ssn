import { RCON } from 'minecraft-server-util';

type MinecraftServer = {
  host: string;
  port: number;
}

export const MinecraftServers = {
  QUEUE: {
    host: 'minecraft-queue',
    port: 25572
  },
  MAIN: {
    host: 'minecraft-main',
    port: 25573
  }
}

const clients = new Map<string, RCON>();

export const connect = async (server: MinecraftServer) => {
  const client = new RCON();
  await client.connect(server.host, server.port);
  await client.login('rcon');
  clients.set(server.host, client);
  return client;
}

export const exec = async (server: MinecraftServer, command: string) => {
  if (!clients.has(server.host)) await connect(server);
  const client = clients.get(server.host);
  if (!client) throw Error('Unknown server');
  await client.run(command);
}

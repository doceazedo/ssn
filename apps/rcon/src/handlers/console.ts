import { Server, Socket } from "socket.io";
import { RCON } from 'minecraft-server-util';

const MinecraftServers = {
  QUEUE: {
    host: 'minecraft-queue',
    port: 25572
  },
  MAIN: {
    host: 'minecraft-main',
    port: 25573
  }
}

const client = new RCON();

export const registerConsoleHandlers = async (io: Server, socket: Socket) => {
  try {
    await client.connect(MinecraftServers.MAIN.host, MinecraftServers.MAIN.port);
    await client.login('rcon');
  } catch (error) {
    socket.emit("message", "Error connecting to RCON");
  }

  client.on('message', async (data) => {
    if (!data.message) return;
    socket.emit("message", data.message);
  });

  const exec = async (command: string) => {
    await client.run(command);
    socket.emit("message", `$ ${command}`);
  }

  socket.on("console:exec", exec);
}

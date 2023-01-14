import { Server, Socket } from "socket.io";
import { connect, MinecraftServers } from 'rcon-helper';
import type { RCON } from 'minecraft-server-util';

export const registerConsoleHandlers = async (io: Server, socket: Socket) => {
  let client: RCON;

  try {
    client = await connect(MinecraftServers.MAIN);
  } catch (error) {
    socket.emit("message", "Error connecting to RCON");
    return;
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

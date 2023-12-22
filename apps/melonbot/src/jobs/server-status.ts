import { status } from "minecraft-server-util";
import type { Client, GuildBasedChannel } from "discord.js";

const GUILD_ID = "980494006863687680";
const PLAYERCOUNT_CHANNEL_ID = "980510315441704980";

let channel: GuildBasedChannel | null = null;

export const serverStatusJob = (client: Client) => {
  setInterval(() => updateServerStatus(client), 10000);
  updateServerStatus(client);
};

const updateServerStatus = async (client: Client) => {
  if (!channel) {
    const guild = await client.guilds.fetch(GUILD_ID);
    channel = await guild.channels.fetch(PLAYERCOUNT_CHANNEL_ID);
  }
  const proxyStatus = await status("ssn.gg");
  channel?.setName(
    `Players online: ${proxyStatus.players.online}/${proxyStatus.players.max}`
  );
};

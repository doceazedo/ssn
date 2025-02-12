import {
  ActionRowBuilder,
  AttachmentBuilder,
  ButtonBuilder,
  ButtonStyle,
  EmbedBuilder,
  SlashCommandBuilder,
} from "discord.js";
import { status } from "minecraft-server-util";
import { Command } from "../types";

type CommanderStatus = {
  ping: number;
  tps: number;
};

const commanderBaseUrl = process.env.LOCAL_COMMANDER_URL;

const statusCommand: Command = {
  data: new SlashCommandBuilder()
    .setName("status")
    .setDescription("Retorna o status atual do servidor"),
  execute: async (interaction: any) => {
    const proxyStatus = await status("ssn.gg");
    const mainStatus = await getCommanderStatus();

    let file: AttachmentBuilder | undefined;
    if (proxyStatus.favicon) {
      const faviconData = proxyStatus.favicon.split(",")[1];
      const faviconBuffer = Buffer.from(faviconData, "base64");
      file = new AttachmentBuilder(faviconBuffer).setName("favicon.png");
    }

    const statusEmbed = new EmbedBuilder()
      .setColor(proxyStatus ? 0x48c78e : 0xf14668)
      .setTitle("Status do SSN.gg")
      .setThumbnail("attachment://favicon.png")
      .setTimestamp()
      .addFields(
        {
          name: `${proxyStatus ? "✅" : "❌"} Status`,
          value: proxyStatus ? "Online" : "Offline",
          inline: true,
        },
        {
          name: "👥 Players online",
          value: `${proxyStatus.players.online}/${proxyStatus.players.max}`,
          inline: true,
        },
        {
          name: "📡 Ping",
          value: `${mainStatus?.ping || proxyStatus.roundTripLatency}ms`,
          inline: true,
        },
        { name: "🧭 Versão", value: "Java Edition 1.21.4", inline: true },
        {
          name: "⚡️ TPS",
          value: mainStatus?.tps?.toFixed(1) || "20.0",
          inline: true,
        },
        { name: "💭 MOTD", value: proxyStatus.motd.clean, inline: true },
      );

    const actions = new ActionRowBuilder().addComponents(
      new ButtonBuilder()
        .setCustomId("copy_ip")
        .setLabel("Copiar IP")
        .setStyle(ButtonStyle.Primary),
      new ButtonBuilder()
        .setCustomId("refresh")
        .setLabel("Atualizar")
        .setStyle(ButtonStyle.Secondary),
    );

    await interaction.reply({
      embeds: [statusEmbed],
      components: [actions],
      files: [file],
    });
  },
};

const getCommanderStatus = async (): Promise<CommanderStatus | null> => {
  try {
    const resp = await fetch(`${commanderBaseUrl}/api/public/status`);
    const data = await resp.json();
    return data;
  } catch (error) {
    return null;
  }
};

export default statusCommand;

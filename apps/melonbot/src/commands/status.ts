import { ActionRowBuilder, AttachmentBuilder, ButtonBuilder, ButtonStyle, EmbedBuilder, SlashCommandBuilder } from 'discord.js';
import { status } from 'minecraft-server-util';
import { Command } from '../types';

const ip = "ssn.gg";

const statusCommand: Command = {
	data: new SlashCommandBuilder()
		.setName('status')
		.setDescription('Retorna o status atual do servidor'),
	execute: async (interaction: any) => {
		const data = await status(ip);

		let file: AttachmentBuilder | undefined;
		if (data.favicon) {
			const faviconData = data.favicon.split(',')[1];
			const faviconBuffer = Buffer.from(faviconData, 'base64');
			file = new AttachmentBuilder(faviconBuffer)
				.setName('favicon.png');
		}

		const statusEmbed = new EmbedBuilder()
			.setColor(data ? 0x48c78e : 0xf14668)
			.setTitle('Status do SSN.gg')
			.setThumbnail('attachment://favicon.png')
			.setTimestamp()
			.addFields(
				{ name: `${data ? '✅' : '❌'} Status`, value: data ? 'Online' : 'Offline', inline: true },
				{ name: '👥 Players online', value: `${data.players.online}/${data.players.max}`, inline: true },
				{ name: '📡 Ping', value: `${data.roundTripLatency}ms`, inline: true },
				{ name: '🧭 Versão', value: 'Java Edition 1.12.2', inline: true },
				{ name: '⚡️ TPS', value: '20.0', inline: true },
				{ name: '💭 MOTD', value: data.motd.clean, inline: true },
			);

		const actions = new ActionRowBuilder()
			.addComponents(
				new ButtonBuilder()
					.setCustomId('copy_ip')
					.setLabel('Copiar IP')
					.setStyle(ButtonStyle.Primary),
				new ButtonBuilder()
					.setCustomId('refresh')
					.setLabel('Atualizar')
					.setStyle(ButtonStyle.Secondary),
			);

		await interaction.reply({ embeds: [statusEmbed], components: [actions], files: [file] });
	}
}

export default statusCommand;

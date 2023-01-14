import fs from 'fs';
import path from 'path';
import { Client, Collection, Events, GatewayIntentBits, REST, Routes, } from 'discord.js';
import { Command } from './types';

const token = process.env.DISCORD_TOKEN;
if (!token) {
  console.error('DISCORD_TOKEN does not exist');
  process.exit(1);
}

const clientId = process.env.PUBLIC_DISCORD_CLIENT_ID;
if (!clientId) {
  console.error('PUBLIC_DISCORD_CLIENT_ID does not exist');
  process.exit(1);
}

const client = new Client({
  intents: [GatewayIntentBits.Guilds]
});
const rest = new REST({ version: '10' }).setToken(token);

const commands: Collection<string, Command> = new Collection();
const commandsPath = path.join(__dirname, 'src/commands');
const commandFiles = fs.readdirSync(commandsPath).filter(file => file.endsWith('.ts'));

for (const file of commandFiles) {
	const filePath = path.join(commandsPath, file);
	const command: Command = (await import(filePath)).default;
	if ('data' in command && 'execute' in command) {
		commands.set(command.data.name, command);
	} else {
		console.warn(`Unable to parse command at "${filePath}": missing properties.`);
	}
}

try {
  console.log(`Started registering ${commands.size} slash commands.`);
  const data: any = await rest.put(
    Routes.applicationCommands(clientId),
    { body: commands.toJSON().map(command => command.data.toJSON()) },
  );
  console.log(`Successfully reloaded ${data.length} slash commands.`);
} catch (error) {
  console.error(error);
}

client.on(Events.InteractionCreate, async (interaction) => {
	if (!interaction.isChatInputCommand()) return;

	const command = commands.get(interaction.commandName);
	if (!command) {
		console.error(`No command matching ${interaction.commandName} was found.`);
		return;
	}

	try {
		command.execute(interaction);
	} catch (error) {
		console.error(error);
		await interaction.reply({ content: 'There was an error while executing this command!', ephemeral: true });
	}
});

client.once(Events.ClientReady, (c) => {
	console.log(`Ready! Logged in as ${c.user.tag}`);
});

client.login(token);

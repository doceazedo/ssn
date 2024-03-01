import "dotenv/config";

type EmbedsMap = {
  [key: string]: {
    title: string;
    description: string;
    color: number;
  };
};

const EMBEDS_MAP: EmbedsMap = {
  "--daily-restart": {
    title: "🚧 Manutenção",
    description:
      "Reiniciando o servidor para manutenção diária automática, segura aí...",
    color: 16498468,
  },
  "--maintenance": {
    title: "🚧 Manutenção",
    description: "O servidor foi fechado para manutenção.",
    color: 16498468,
  },
  "--maintenance-done": {
    title: "✅ Servidor online",
    description: "Manutenção finalizada e servidor reaberto. Bom jogo!",
    color: 4769678,
  },
};

const validArgs = Object.keys(EMBEDS_MAP).join(", ");

const arg = process.argv.find((x) => x.startsWith("--"));
if (!arg) {
  console.log(`No arg used: ${validArgs}`);
  process.exit(1);
}

const embed = EMBEDS_MAP[arg];
if (!embed) {
  console.log(`Invalid arg: ${validArgs}`);
  process.exit(1);
}

try {
  const resp = await fetch(`${process.env.DISCORD_WEBHOOK_CRONJOB}`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({
      embeds: [embed],
    }),
  });
  if (!resp.ok) throw Error();
  console.log("Webhook sent");
} catch (error) {
  console.log("Could not send webhook");
  process.exit(1);
}

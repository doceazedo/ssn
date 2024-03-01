import "dotenv/config";

const args = process.argv.slice(2);
const message = args?.[0] || null;
if (!message) {
  console.log("No message informed");
  process.exit(1);
}

try {
  const resp = await fetch(`http://localhost:25574/api/private/broadcast`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${process.env.COMMANDER_TOKEN}`,
    },
    body: JSON.stringify({
      message,
    }),
  });
  if (!resp.ok) throw Error();
  console.log("Broadcast sent");
} catch (error) {
  console.log("Could not broadcast message");
  process.exit(1);
}

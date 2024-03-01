import "dotenv/config";

const args = process.argv.slice(2);
const reason = args?.[0] || null;
if (!reason) {
  console.log("No reason informed");
  process.exit(1);
}

try {
  const resp = await fetch("http://localhost:25574/api/private/kick-all", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${process.env.COMMANDER_TOKEN}`,
    },
    body: JSON.stringify({
      reason,
    }),
  });
  if (!resp.ok) throw Error();
  console.log("All players were kicked");
} catch (error) {
  console.log("Could not kick players");
  process.exit(1);
}

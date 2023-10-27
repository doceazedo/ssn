<script lang="ts">
  import {
    DiscordAltIcon,
    JoystickIcon,
    ServerIcon,
    ServerOffIcon,
    SkullIcon,
    SproutIcon,
    UsersIcon,
  } from "../icons";
  import { Card } from "../components";
  import type { JavaStatusResponse } from "minecraft-server-util";

  type Status = JavaStatusResponse | false | null;

  export let status: Status = null;

  const getStatusTitle = (status: Status) => {
    if (status === null) return "Carregando...";
    return status ? "Server online" : "Server fechado";
  };
</script>

<Card
  title={getStatusTitle(status)}
  icon={status === false ? ServerOffIcon : ServerIcon}
>
  <ul class="server-status">
    <li>
      <UsersIcon />
      {#if !!status}
        {status.players.online}/{status.players.max} online
      {:else}
        0/0 online
      {/if}
    </li>
    <li>
      <JoystickIcon />
      <span>
        Java Edition 1.19.4
        <small>Compatível com 1.9.x ou superior</small>
      </span>
    </li>
    <li><SkullIcon /> Pirata e original</li>
    <li><SproutIcon /> Seed: 372733000726032955</li>
  </ul>
</Card>

<Card title="Discord" icon={DiscordAltIcon}>
  <iframe
    class="widget"
    title="Discord widget"
    src="https://discord.com/widget?id=980494006863687680&theme=dark"
    allowtransparency={true}
    frameborder={0}
    sandbox="allow-popups allow-popups-to-escape-sandbox allow-same-origin allow-scripts"
  />
</Card>

<style lang="sass">
  .server-status
    display: flex
    flex-direction: column
    gap: .25rem

    li
      display: flex
      align-items: center
      gap: .5rem

    span
      display: flex
      flex-direction: column

    small
      font-size: .75rem
      opacity: .75

  .widget
    width: 100%
    height: 32rem
</style>

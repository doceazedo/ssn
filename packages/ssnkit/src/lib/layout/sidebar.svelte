<script lang="ts">
  import { Joystick, Server, ServerOff, Skull, Sprout, Twitter, Users } from 'lucide-svelte';
  import { Card } from '../components';
  import { DiscordAltIcon } from '../icons';
  import type { JavaStatusResponse } from 'minecraft-server-util';

  export let status: JavaStatusResponse | null = null;
</script>

<Card
  title={status ? 'Server online' : 'Server fechado'}
  icon={status ? Server : ServerOff}
>
  <ul class="server-status">
    <li><Users /> {status?.players?.online || 0}/{status?.players?.max || 0} online</li>
    <li><Joystick /> Java Edition 1.12.2</li>
    <li><Skull /> Pirata e original</li>
    <li><Sprout /> Seed: 0000000000000000000</li>
  </ul>
</Card>

<Card
  title="Discord"
  icon={DiscordAltIcon}
>
  <iframe
    class="widget"
    title="Discord widget"
    src="https://discord.com/widget?id=980494006863687680&theme=dark"
    allowtransparency={true}
    frameborder={0}
    sandbox="allow-popups allow-popups-to-escape-sandbox allow-same-origin allow-scripts"
  />
</Card>

<Card
  title="Twitter"
  icon={Twitter}
>
  <div class="widget">
    <a
      class="twitter-timeline"
      href="https://twitter.com/servidorsemnome?ref_src=twsrc%5Etfw"
      data-height={512}
    >
        Tweets by servidorsemnome
    </a>
    <script async src="https://platform.twitter.com/widgets.js" charset="utf-8" />
  </div>
</Card>

<style lang="sass">
  .server-status li
    display: flex
    align-items: center
    gap: .5rem

  .widget
    width: 100%
    height: 32rem
</style>

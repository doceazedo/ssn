<script lang="ts">
  import { io } from 'socket.io-client';
  import { Checkbox } from 'ssnkit';
	import { tick } from 'svelte';

  const socket = io('ws://rcon.ssn.local', {
    withCredentials: true
  });

  socket.on("connect", async () => {
    await appendMessage('Connected to RCON');
    await appendMessage(`Socket ID: ${socket.id}`);
  });

  socket.on("message", (message) => appendMessage(message));

  const sendCommand = async () => {
    socket.emit("console:exec", command);
    command = '';
  }

  const appendMessage = async (newMessage: string) => {
    messages = [...messages, newMessage].slice(0, 50);
    if (autoscroll) {
      await tick();
      consoleEl.scrollTo(0, consoleEl.scrollHeight);
    }
  }

  let command = '';
  let messages: string[] = [];
  let consoleEl: HTMLUListElement;
  let autoscroll = true;
</script>

<div class="console-wrapper">
  <ul class="console" bind:this={consoleEl}>
    {#each messages as message}
      <li>{message}</li>
    {/each}
  </ul>

  <form class="console-input" on:submit|preventDefault={sendCommand}>
    <input bind:value={command} type="text" placeholder="/help">
    <button type="submit" class="button is-primary is-small">Enviar</button>
  </form>

  <Checkbox bind:checked={autoscroll}>
    Auto scroll
  </Checkbox>
</div>

<style lang="sass">
  @import url('https://fonts.googleapis.com/css2?family=IBM+Plex+Mono&display=swap')

  .console-wrapper
    display: flex
    flex-direction: column
    gap: 1rem

  .console,
  .console-input input
    font-family: 'IBM Plex Mono', monospace
    background-color: #0a0a0a
    color: #fff

  .console
    border-radius: .5rem
    white-space: pre
    height: 512px
    overflow-y: auto

    li
      width: 100%
      white-space: break-spaces
      padding: .25rem .5rem

      &:nth-child(even)
        background-color: #1b1b1b

  .console-input
    display: flex

    input
      flex-grow: 1
      padding: .25rem .5rem
      border-top-left-radius: .5rem
      border-bottom-left-radius: .5rem
      border: none

    .button
      border-top-left-radius: 0 !important
      border-bottom-left-radius: 0 !important
</style>
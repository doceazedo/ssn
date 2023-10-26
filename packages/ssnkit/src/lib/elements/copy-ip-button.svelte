<script>
  import { CheckIcon, CopyIcon } from "../icons";

  export let playerCount = 0;

  const ip = "ssn.gg";
  let showCheckmark = false;

  const copy = () => {
    navigator.clipboard.writeText(ip);
    showCheckmark = true;
    setTimeout(() => (showCheckmark = false), 1000);
  };
</script>

<button class="ip-button" on:click={copy}>
  {#if showCheckmark}
    <CheckIcon size={32} />
  {:else}
    <CopyIcon size={32} />
  {/if}
  <div>
    <h1>IP: {ip}</h1>
    <p>{playerCount} jogadores online</p>
  </div>
</button>

<style lang="sass">
  .ip-button
    display: flex
    align-items: center
    text-align: left
    gap: .75rem
    padding: 1rem
    background-color: rgba(#000, .25)
    color: #fff
    border: none
    border-radius: 1rem
    cursor: pointer
    transition: all .2s ease

    &:hover
      transform: translateY(-.2rem)
  
    h1
      font-weight: bold
      font-size: 1.2rem

  @media screen and (max-width: 768px)
    .ip-button
      padding: .75rem
      gap: .5rem
      border-radius: .75rem

      h1
        font-size: 1.1rem

      :global(svg)
        height: 1.75rem
        width: 1.75rem
</style>

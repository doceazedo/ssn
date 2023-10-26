<script lang="ts">
  import { CheckIcon, ClipboardIcon } from "../icons";

  const ip = "ssn.gg";
  let showCheckmark = false;

  const copy = () => {
    navigator.clipboard.writeText(ip);
    showCheckmark = true;
    setTimeout(() => (showCheckmark = false), 1000);
  };
</script>

<button class="ip-tag" on:click={copy}>
  {#if showCheckmark}
    <CheckIcon size={16} />
  {:else}
    <ClipboardIcon size={16} />
  {/if}

  {ip}
</button>

<style lang="sass">
  @import '../../../styles/vars'

  .ip-tag
    display: inline-flex
    align-items: center
    gap: .25rem
    font-size: inherit
    color: $primary
    background-color: rgba($primary, .25)
    border: none
    border-radius: .5rem
    cursor: pointer
    user-select: text

    &:active
      color: #fff
      background-color: $primary
</style>

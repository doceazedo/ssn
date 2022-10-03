<script lang="ts">
  import Radio from './radio.svelte';

  type Option = {
    value: any;
    label: string;
  }

  export let options: Option[];
  export let value;
</script>

<div class="radio-group">
  {#each options as option}
    <Radio
      value={option.value}
      label={option.label}
      checked={option.value == value}
      on:click={() => (value = option.value)}
    />
  {/each}
</div>

<style lang="sass">
  .radio-group
    display: flex
    flex-direction: column
    gap: .25rem
</style>
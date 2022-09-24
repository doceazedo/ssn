<script lang="ts">
  import { slide } from 'svelte/transition';
  import { quintOut } from 'svelte/easing';
  import { DiscordIcon, Input, Separator } from 'ssnkit';

  type Field = {
    label: string;
    name: string;
    value: string;
    type?: string;
  };

  type Link = {
    label: string;
    href: string;
  }

  export let fields: Field[] = [];
  export let links: Link[] = [];
  export let isLoading = false;
  export let error: string;
  export let discordLabel;
  export let submitLabel;
</script>

<form class="form" class:is-loading={isLoading} on:submit|preventDefault>
  <a href="/oauth/discord" class="button is-link is-light is-fullwidth">
    <DiscordIcon />
    {discordLabel}
  </a>
  <Separator />
  {#each fields as field}
    <div class="field">
      <Input
        name={field.name}
        label={field.label}
        type={field.type}
        disabled={isLoading}
        bind:value={field.value}
      />
    </div>
  {/each}

  {#if error}
    <div
      class="notification is-danger is-light"
      transition:slide={{ duration: 200, easing: quintOut }}
    >
      {error}
    </div>
  {/if}

  <button
    type="submit"
    class="button is-primary is-fullwidth"
    class:is-loading={isLoading}
  >
    {submitLabel}
  </button>
</form>

<div class="mt-auto">
  {#each links as link}
    <p><a class="has-text-grey" href={link.href}>{link.label}</a></p>
  {/each}
</div>

<style lang="sass">
  .form
    transition: all .2s ease

    &.is-loading
      opacity: .75
      pointer-events: none
</style>
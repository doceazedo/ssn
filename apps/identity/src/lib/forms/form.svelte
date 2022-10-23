<script lang="ts">
  import { slide } from 'svelte/transition';
  import { quintOut } from 'svelte/easing';
  import { page } from "$app/stores";
  import { DiscordIcon, Input, Separator } from 'ssnkit';
  import { discordClientId, identityUrl } from "$lib/env/public";

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

  const redirect = $page.url.searchParams.get('redirect');
  const redirectParams = redirect ? `?redirect=${redirect}` : '';

  const discordRedirectUrl = encodeURI(`${identityUrl}/auth/discord`);
  const discordUrl = `https://discord.com/oauth2/authorize?client_id=${discordClientId}&redirect_uri=${discordRedirectUrl}&response_type=code&scope=identify`;
</script>

<form class="form" class:is-loading={isLoading} on:submit|preventDefault>
  <a href={discordUrl} class="button is-link is-light is-fullwidth">
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
    <p><a class="has-text-grey" href={link.href + redirectParams}>{link.label}</a></p>
  {/each}
</div>

<style lang="sass">
  .form
    transition: all .2s ease

    &.is-loading
      opacity: .75
      pointer-events: none
</style>
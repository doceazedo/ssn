<script lang="ts">
	import { browser } from "$app/environment";
	import { env } from "$env/dynamic/public";
	import { onMount } from "svelte";

  const turnstileSiteKey = env.PUBLIC_CF_TURNSTILE_SITE_KEY;

  export let token = '';

  const renderTurnstile = () => {
    window.turnstile.render('#cf-turnstile', {
      sitekey: turnstileSiteKey,
      theme: 'light',
      callback: (_token: string) => token = _token,
    })
  }

  onMount(() => {
    if (!browser || !window.turnstile || !turnstileSiteKey) return;
    renderTurnstile();
  });
</script>

{#if turnstileSiteKey}
  <div id="cf-turnstile" />
{/if}

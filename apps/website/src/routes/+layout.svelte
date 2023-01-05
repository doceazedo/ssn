<script lang="ts">
  import 'ssnkit/styles';
  import { Layout, Metadata } from 'ssnkit';
  import { page } from '$app/stores';
  import { env } from '$env/dynamic/public';
  import { IDENTITY } from "$lib/auth";
  import { SERVER_STATUS } from '$lib/status';
  import type { LayoutServerData } from './$types';

  export let data: LayoutServerData;

  $: currentUrl = $page.url.href.endsWith('/') ? $page.url.href.slice(0, -1) : $page.url.href;

  $IDENTITY = data.identity || null;
</script>

<Metadata {currentUrl} />

<Layout
  {currentUrl}
  websiteBaseUrl={env.PUBLIC_WEBSITE_URL || ''}
  identityBaseUrl={env.PUBLIC_IDENTITY_URL || ''}
  identity={$IDENTITY}
  playerCount={$SERVER_STATUS?.players?.online}
>
  <slot />
</Layout>

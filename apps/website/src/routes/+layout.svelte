<script lang="ts">
  import 'ssnkit/styles';
  import { Layout } from 'ssnkit';
  import { page } from '$app/stores';
  import { env } from '$env/dynamic/public';
  import { IDENTITY } from "$lib/auth";
  import { SERVER_STATUS } from '$lib/status';
  import { CURRENT_URL } from '$lib/stores';
  import type { LayoutServerData } from './$types';

  export let data: LayoutServerData;

  $CURRENT_URL = $page.url.href.endsWith('/') ? $page.url.href.slice(0, -1) : $page.url.href;

  $IDENTITY = data.identity || null;
</script>

<Layout
  currentUrl={$CURRENT_URL}
  websiteBaseUrl={env.PUBLIC_WEBSITE_URL || ''}
  identityBaseUrl={env.PUBLIC_IDENTITY_URL || ''}
  identity={$IDENTITY}
  playerCount={$SERVER_STATUS?.players?.online}
>
  <slot />
</Layout>

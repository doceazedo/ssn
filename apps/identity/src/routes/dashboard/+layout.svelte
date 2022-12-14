<script lang="ts">
  import { Layout } from 'ssnkit';
  import { Menu } from '$lib/components';
  import { page } from '$app/stores';
  import { env } from '$env/dynamic/public';
  import { IDENTITY } from "$lib/auth";

  $: currentUrl = $page.url.href.endsWith('/') ? $page.url.href.slice(0, -1) : $page.url.href;
</script>

<Layout
  {currentUrl}
  websiteBaseUrl={env.PUBLIC_WEBSITE_URL || ''}
  identityBaseUrl={env.PUBLIC_IDENTITY_URL || ''}
  identity={$IDENTITY}
>
  <div class="dashboard">
    <Menu />
    <main class="main">
      <slot />
    </main>
  </div>
</Layout>

<style lang="sass">
  .dashboard
    display: flex
    gap: 3rem

    .main
      width: 100%
</style>
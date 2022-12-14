<script lang="ts">
  import { Footer, Header } from '.';
  import type { SafeIdentity } from "warehouse";

  export let identity: SafeIdentity | null;
  export let websiteBaseUrl: string;
  export let identityBaseUrl: string;
  export let currentUrl = '';
</script>

<Header {websiteBaseUrl} {identityBaseUrl} {currentUrl} {identity} />
<main class="section">
  <div class="container">
    <slot />
  </div>
</main>
<Footer />

<style lang="sass">
  :global(body:has(main.section))
    background-color: #f1f5f9
</style>
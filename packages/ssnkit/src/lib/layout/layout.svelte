<script lang="ts">
  import { Footer, Header } from '.';
  import type { SafeIdentity } from "warehouse";

  export let identity: SafeIdentity;
</script>

<Header {identity} />
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
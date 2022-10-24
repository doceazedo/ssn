<script lang="ts">
  import { Layout } from 'ssnkit';
  import { Menu } from '$lib/components';
  import { IDENTITY} from "$lib/auth";
</script>

<Layout identity={$IDENTITY}>
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
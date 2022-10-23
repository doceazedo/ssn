<script lang="ts">
  import { Layout } from 'ssnkit';
  import { Menu } from '$lib/components';
  import { IDENTITY} from "$lib/auth";
</script>

<Layout identity={$IDENTITY}>
  <div class="dashboard">
    <Menu />
    <div>
      <slot />
    </div>
  </div>
</Layout>

<style lang="sass">
  .dashboard
    display: flex
    gap: 3rem
</style>
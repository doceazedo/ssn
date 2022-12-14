<script lang="ts">
  import 'ssnkit/styles';
  import { Layout } from 'ssnkit';
  import { IDENTITY } from "$lib/auth";
  import type { LayoutServerData } from './$types';

  export let data: LayoutServerData;

  $IDENTITY = data.identity;
</script>

<Layout identity={$IDENTITY}>
  <slot />
</Layout>
<script lang="ts">
  import 'ssnkit/styles';
  import { IDENTITY } from "$lib/auth";
  import type { LayoutServerData } from './$types';

  export let data: LayoutServerData;

  $IDENTITY = data.identity;
</script>

<slot />
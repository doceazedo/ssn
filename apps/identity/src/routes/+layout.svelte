<script lang="ts">
  import 'ssnkit/styles';
  import { Metadata } from 'ssnkit';
  import { page } from '$app/stores';
  import { IDENTITY } from "$lib/auth";
  import type { LayoutServerData } from './$types';

  export let data: LayoutServerData;

  $IDENTITY = data.identity;
</script>

<Metadata appTitle="SSN Identity" currentUrl={$page.url.href} />

<slot />
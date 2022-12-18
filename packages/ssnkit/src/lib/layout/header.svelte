<script lang="ts">
  import { Hero, Navbar } from '.';
  import type { SafeIdentity } from "warehouse";

  export let identity: SafeIdentity;
  export let websiteBaseUrl: string;
  export let identityBaseUrl: string;
  export let currentUrl = '';
</script>

<Hero {websiteBaseUrl} />
<Navbar {websiteBaseUrl} {identityBaseUrl} {currentUrl} {identity} />

<script lang="ts">
  import { Hero, Navbar } from '.';
  import type { SafeIdentity } from "warehouse";

  export let identity: SafeIdentity;
</script>

<Hero />
<Navbar {identity} />
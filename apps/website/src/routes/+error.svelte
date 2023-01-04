<script lang="ts">
  import { page } from '$app/stores';
  import { HttpErrorDisplay } from 'ssnkit';
</script>

<HttpErrorDisplay code={$page.status} />

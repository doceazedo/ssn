<script lang="ts">
  import { page } from '$app/stores';
  import { HttpErrorDisplay } from 'ssnkit';
</script>

<section class="section">
  <div class="container">
    <HttpErrorDisplay code={$page.status} />
  </div>
</section>

<script lang="ts">
	import { page } from '$app/state';
</script>

<h1 class="text-2xl font-bold">Minha conta</h1>
<hr />
<pre>{JSON.stringify(page.data.user, null, 2)}</pre>

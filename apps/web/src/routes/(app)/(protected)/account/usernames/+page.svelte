<script lang="ts">
	import { page } from '$app/state';
	import WipPage from '$lib/components/WipPage.svelte';
	import * as m from '$lib/paraglide/messages.js';
	import { pb } from '$lib/pocketbase';
</script>

<h1 class="text-2xl font-bold">Nomes de usuário</h1>
<hr />
<WipPage />
<p>Nomes de usuário registrados:</p>
<ul class="list-inside list-disc pl-6">
	{#await pb.collection('usernames').getFullList()}
		<li>[...]</li>
	{:then result}
		{#each result as username}
			<li>{username.name}</li>
		{/each}
	{/await}
</ul>
<p>
	Você pode fazer login com mesmo e-mail e senha ao entrar com qualquer nome de usuário que você
	quiser, e eles aparecerão aqui.
</p>

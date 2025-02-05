<script lang="ts">
	import { goto } from '$app/navigation';
	import { page } from '$app/state';
	import { Button } from '$lib/components/ui/button';
	import { pb } from '$lib/pocketbase';
	import { PRIMARY_USERNAME } from '$lib/stores';
	import { SignOut, Users } from 'phosphor-svelte';

	let { children } = $props();

	const logout = () => {
		pb.authStore.clear();
		goto('/', { invalidateAll: true });
	};
</script>

<header class="flex justify-center my-12">
	<a class="w-fit" href="/">
		<img src="/img/ssn-icon.png" alt="" class="h-12" style="user-select: none; user-drag: none"
		draggable="false" />
	</a>
</header>

<div class="mx-auto flex w-full max-w-6xl gap-6">
	<aside class="flex h-fit w-full max-w-xs flex-col gap-1.5 rounded-lg border p-6">
		<Button href="/account" variant={page.route.id === '/(app)/(protected)/account' ? 'secondary' : 'ghost'} class="justify-start border-none">
			<img
				src="https://mc-heads.net/avatar/{$PRIMARY_USERNAME?.name || 'MHF_Alex'}/64"
				alt=""
				class="size-5 rounded object-cover"
			/>
			Minha conta
		</Button>
    <Button href="/account/usernames" variant={page.route.id === '/(app)/(protected)/account/usernames' ? 'secondary' : 'ghost'} class="justify-start border-none">
			<Users size={20} />
			Nomes de usuário
		</Button>
		<hr class="my-1.5" />
		<Button
			variant="ghost"
			class="hover:bg-destructive/20 hover:text-destructive justify-start"
			onclick={logout}
		>
			<SignOut size={20} />
			Desconectar
		</Button>
	</aside>
	<main class="flex w-full flex-col gap-3 rounded-lg border p-6">
		{@render children()}
	</main>
</div>

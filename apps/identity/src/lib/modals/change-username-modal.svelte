<script lang="ts">
	import { Input } from 'ssnkit';
	import { fade, scale, slide } from 'svelte/transition';
	import { quintOut } from 'svelte/easing';
	import { changeUsername, IDENTITY } from '$lib/auth';
	import { Captcha } from '$lib/captcha';
	import Error from '../../routes/+error.svelte';

	export let isOpen = false;

	let newUsername = '';
	let captchaToken = '';
	let isLoading = false;
	let error = '';

	$: isOpen, (error = '');

	const close = () => {
		if (!isLoading) isOpen = false;
	};

	const handleSubmit = async () => {
		if (isLoading) return;
		if (!$IDENTITY) throw Error('Você não está logado');
		try {
			isLoading = true;
			error = '';

			if (!newUsername.length) throw Error('Digite um nome de usuário');
			await changeUsername($IDENTITY.primaryUsername, newUsername, captchaToken);
		} catch (e) {
			isLoading = false;
			error = e.message;
		}
	};
</script>

{#if isOpen}
	<div class="modal is-active" transition:fade={{ duration: 200, easing: quintOut }}>
		<div class="modal-background" on:click={close}></div>
		<div
			class="modal-card"
			transition:scale={{ duration: 200, opacity: 1, start: 0.75, easing: quintOut }}
		>
			<header class="modal-card-head">
				<p class="modal-card-title">Alterar nome de usuário</p>
				<button class="delete" aria-label="close" on:click={close} />
			</header>
			<section class="modal-card-body">
				<div class="content">
					<p class="mb-5">
						Como você ainda não entrou no servidor, você ainda pode trocar o seu nome de usuário.
						Por favor, confira se o nome que você vai preencher é exatamente igual ao da conta do
						Minecraft que você quer usar em jogo.
					</p>
					<div class="mb-5">
						<Input
							bind:value={newUsername}
							disabled={isLoading}
							name="newusername"
							label="Novo nome de usuário"
						/>
					</div>
					<Captcha bind:token={captchaToken} />
					{#if error}
						<div
							class="notification is-danger is-light mt-4"
							transition:slide={{ duration: 200, easing: quintOut }}
						>
							{error}
						</div>
					{/if}
				</div>
			</section>
			<footer class="modal-card-foot">
				<button class="button is-primary" class:is-loading={isLoading} on:click={handleSubmit}
					>Alterar nome</button
				>
				<button class="button" disabled={isLoading} on:click={close}>Cancelar</button>
			</footer>
		</div>
	</div>
{/if}

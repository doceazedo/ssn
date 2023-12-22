<script lang="ts">
	import { Input, Switch } from 'ssnkit';
	import { fade, scale, slide } from 'svelte/transition';
	import { quintOut } from 'svelte/easing';
	import { browser } from '$app/environment';
	import { deleteUsername } from '$lib/username';

	export let isOpen = false;
	export let username: string;

	let isLoading = false;
	let error = '';
	let confirmInput = '';
	let deleteIngameData = false;

	$: confirmed = confirmInput === username;

	const close = () => {
		if (!isLoading) isOpen = false;
	};

	const handleConfirm = async () => {
		if (isLoading || !username || !confirmed) return;
		try {
			isLoading = true;
			await deleteUsername(username, deleteIngameData);
			if (browser) location.reload();
		} catch (e: any) {
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
				<p class="modal-card-title">Abandonar conta</p>
				<button class="delete" aria-label="close" on:click={close} />
			</header>
			<section class="modal-card-body">
				<div class="content">
					<p>
						Você tem certeza que deseja abandonar a conta <b>{username}</b>? Você ainda poderá jogar
						com suas outras contas, mas:
					</p>
					<ul>
						<li>Você não poderá mais entrar no jogo com esse nome de usuário</li>
						<li>Qualquer pessoa poderá se cadastrar com esse nome de usuário</li>
						<li>O perfil, estatísticas e emblemas desse usuário serão apagados</li>
						<li class="strike" class:is-active={deleteIngameData}>
							O inventário dessa conta será mantido dentro do jogo
						</li>
					</ul>
					<Switch bind:checked={deleteIngameData}>Excluir todos os dados em jogo dessa conta</Switch
					>
					<hr />
					<div class="notification is-danger is-light">
						Ao abandonar essa conta, ela não poderá ser recuperada. Para confirmar que você entende,
						digite "<b>{username}</b>" no campo abaixo:
					</div>
					<Input bind:value={confirmInput} label="Código de confirmação" name="confirm" />
				</div>
				{#if error}
					<div
						class="notification is-danger is-light mt-4"
						transition:slide={{ duration: 200, easing: quintOut }}
					>
						{error}
					</div>
				{/if}
			</section>
			<footer class="modal-card-foot">
				<button
					class="button is-primary is-danger"
					class:is-loading={isLoading}
					disabled={!confirmed}
					on:click={handleConfirm}
				>
					Confirmar
				</button>
				<button class="button" disabled={isLoading} on:click={close}>Cancelar</button>
			</footer>
		</div>
	</div>
{/if}

<style>
	.strike {
		position: relative;
		width: fit-content;
		transition: all 0.2s ease;
	}

	.strike::before {
		content: '';
		position: absolute;
		top: 12px;
		width: 0;
		height: 1px;
		background-color: #333;
		transition: all 0.4s ease;
	}

	.strike.is-active {
		opacity: 0.5;
	}

	.strike.is-active::before {
		width: calc(100% - 6px);
	}

	@media screen and (max-width: 768px) {
		.strike.is-active {
			text-decoration: line-through;
		}

		.strike::before {
			content: unset;
		}
	}
</style>

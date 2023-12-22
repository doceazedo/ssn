<script lang="ts">
	import dayjs from 'dayjs';
	import { PageTitle } from 'ssnkit';
	import { Plus } from 'lucide-svelte';
	import { IDENTITY } from '$lib/auth';
	import {
		ChangeUsernameModal,
		DeleteUsernameModal,
		NewUsernameModal,
		PrimaryUsernameModal
	} from '$lib/modals';
	import type { Username } from '@prisma/client';
	import type { PageServerData } from './$types';

	export let data: PageServerData;

	let isNewUserModalOpen = false;
	let isChangeUsernameModalOpen = false;
	let isDeleteUserModalOpen = false;

	let isPrimaryUserModalOpen = false;
	let targetPrimaryUsername = '';
	let targetDeleteUsername = '';

	const getFullDate = (date: Date) => dayjs(date).format('DD/MM/YYYY [às] HH:mm:ss');

	const intentUpdateMainUser = (username: Username) => {
		targetPrimaryUsername = username.name;
		isPrimaryUserModalOpen = true;
	};

	const intentDeleteUser = (username: Username) => {
		targetDeleteUsername = username.name;
		isDeleteUserModalOpen = true;
	};
</script>

<PageTitle pretitle="Geral" title="Minhas contas" class="mb-6">
	<button class="button is-primary is-small" on:click={() => (isNewUserModalOpen = true)}>
		<span class="icon">
			<Plus />
		</span>
		<span> Adicionar nova </span>
	</button>
</PageTitle>

<div class="usernames">
	{#each data.usernames || [] as username}
		<div class="card">
			<div class="card-content">
				<img class="avatar" src="https://mc-heads.net/head/{username.name}" alt="" />
				<div class="info">
					<h1 class="title is-5 mb-1">
						{username.name}
						{#if $IDENTITY?.primaryUsername === username.name}
							<span class="tag is-primary">Principal</span>
						{/if}
						{#if username.isOnline}
							<span class="tag is-primary">Online</span>
						{:else}
							<span class="tag">Offline</span>
						{/if}
					</h1>
					<p>
						{#if username.lastSeen}
							Visto por último em {getFullDate(username.lastSeen)}
						{:else}
							<i>Ainda não se conectou</i>
						{/if}
					</p>
				</div>
				<div class="buttons ml-auto">
					{#if $IDENTITY?.primaryUsername !== username.name}
						<button on:click={() => intentUpdateMainUser(username)} class="button is-small">
							Tornar principal
						</button>
						{#if data.usernames && data.usernames.length > 1}
							<button on:click={() => intentDeleteUser(username)} class="button is-danger is-small">
								Excluir
							</button>
						{/if}
					{/if}

					{#if data.usernames?.length === 1 && !username.firstJoin}
						<button
							class="button is-warning is-small"
							on:click={() => (isChangeUsernameModalOpen = true)}
						>
							Alterar nome de usuário
						</button>
					{/if}
				</div>
			</div>
		</div>
	{/each}
</div>

<NewUsernameModal bind:isOpen={isNewUserModalOpen} />
<DeleteUsernameModal bind:isOpen={isDeleteUserModalOpen} username={targetDeleteUsername} />
<PrimaryUsernameModal bind:isOpen={isPrimaryUserModalOpen} username={targetPrimaryUsername} />
{#if data.usernames?.length === 1}
	<ChangeUsernameModal bind:isOpen={isChangeUsernameModalOpen} />
{/if}

<style lang="sass">
  .usernames
    display: flex
    flex-direction: column
    gap: 1.5rem

    .card-content
      display: flex
      align-items: center
      gap: 1.25rem

      .avatar
        height: 4rem
</style>

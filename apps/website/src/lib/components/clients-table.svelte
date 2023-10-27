<script lang="ts">
	const clients = [
		{
			name: 'Meteor',
			version: '1.20.2',
			openSource: true,
			link: 'https://meteorclient.com',
			logo: 'meteor.png'
		},
		{
			name: 'Aristois',
			version: '1.20.2',
			openSource: false,
			link: 'https://aristois.net',
			logo: 'aristois.png'
		},
		{
			name: 'Ares',
			version: '1.12 - 1.18',
			openSource: true,
			link: 'https://aresclient.org',
			logo: 'ares.png'
		},
		{
			name: 'Inertia',
			version: '1.12 - 1.16',
			openSource: false,
			link: 'https://inertiaclient.com',
			logo: 'inertia.png'
		}
	];
</script>

<div class="clients">
	{#each clients as client}
		<a href={client.link} target="_blank" rel="noopener noreferrer" class="client">
			<img src="/assets/img/clients/{client.logo}" alt="" class="logo" />
			<p class="title">{client.name}</p>
			<p class="feature">{client.version}</p>
			<p class="feature">
				{#if client.openSource}
					✅ Código aberto
				{:else}
					🔒 Código fechado
				{/if}
			</p>
		</a>
	{/each}
</div>

<style>
	.clients {
		display: grid;
		grid-template-columns: repeat(4, 1fr);
		grid-column-gap: 1rem;
		grid-row-gap: 1rem;
	}

	.client {
		display: flex;
		flex-direction: column;
		align-items: center;
		padding: 1rem 0;
		color: inherit;
		border-radius: 0.5rem;
		transition: all 0.2s ease;
	}

	.client:hover {
		transform: scale(1.025);
		background-color: rgba(0, 0, 0, 0.025);
	}

	.client .logo {
		width: 5rem;
		height: 5rem;
		margin-bottom: 1rem;
	}

	.client .title,
	.client .feature {
		margin-bottom: 0;
	}

	.client .title {
		font-size: 1.2rem;
		margin-bottom: 0.375rem;
	}

	.client .feature {
		line-height: 1.5;
	}

	@media screen and (max-width: 1215px) {
		.clients {
			grid-template-columns: repeat(2, 1fr);
		}
	}

	@media screen and (max-width: 768px) {
		.client .logo {
			width: 4rem;
			height: 4rem;
			margin-bottom: 0.75rem;
		}

		.client .title {
			font-size: 1rem;
		}

		.client .feature {
			font-size: 0.8rem;
		}
	}
</style>

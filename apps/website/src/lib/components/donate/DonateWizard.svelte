<script lang="ts">
	import { Card, HandCoinsIcon } from 'ssnkit';
	import { env } from '$env/dynamic/public';
	import { IDENTITY } from '$lib/auth';
	import UsernamePicker from 'ssnkit/src/lib/controls/username-picker.svelte';

	const donationOptions = [10, 20, 40, 60, 80];

	let amount = 20;
	let customAmount: number | null = null;
	let username = $IDENTITY?.primaryUsername || '';

	$: finalAmount = Math.floor(customAmount || amount);
	$: isValidAmount = finalAmount >= 10 && finalAmount <= 500;

	const pickAmount = (value: number) => {
		amount = value;
		customAmount = null;
	};

	const amountToDuration = (amount: number): string => {
		const days = amount >= 10 ? (amount * 30) / 20 : 0;

		if (days <= 0) return '0 dias';

		const months = Math.floor(days / 30);
		const remainingDays = Math.floor(days % 30);

		if (months > 0 && remainingDays > 0) {
			return `${months} ${months === 1 ? 'mês' : 'meses'} e ${remainingDays} ${
				remainingDays === 1 ? 'dia' : 'dias'
			}`;
		} else if (months > 0) {
			return `${months} ${months === 1 ? 'mês' : 'meses'}`;
		} else {
			return `${remainingDays} ${remainingDays === 1 ? 'dia' : 'dias'}`;
		}
	};
</script>

<Card title="Fazer uma doação" icon={HandCoinsIcon}>
	<div class="content">
		{#if $IDENTITY}
			<p>Escolha o valor da sua doação, com mínimo de R$ 10:</p>
			<div class="donation-options">
				{#each donationOptions as option}
					<button
						class="button"
						class:is-active={amount == option && !customAmount}
						on:click={() => pickAmount(option)}
					>
						R$ {option}
					</button>
				{/each}
				<div class="field has-addons">
					<p class="control">
						<span class="button is-static">R$</span>
					</p>
					<p class="control">
						<input class="input" type="number" placeholder="100" bind:value={customAmount} />
					</p>
				</div>
			</div>
			{#if isValidAmount}
				<p class="notification is-info is-light">
					Doando <b>R$ {finalAmount}</b> você terá todos os benefícios acima disponíveis por
					<b>{amountToDuration(finalAmount)}</b>.
				</p>
			{:else}
				<p class="notification is-danger is-light">
					Por favor, escolha um valor entre <b>R$ 10</b> e <b>R$ 500</b>.
				</p>
			{/if}
			<p>
				Escolha a conta que você quer aplicar os benefícios em jogo — você só pode escolher uma à
				cada doação, <b>selecione com atenção</b>:
			</p>
			<UsernamePicker usernames={$IDENTITY.usernames} bind:currentUsername={username} />
			<p class="mt-3">
				<small>
					Os benefícios serão aplicados na sua conta automaticamente assim que o pagamento for
					confirmado. Caso algum dos benefícios não sejam aplicados automaticamente, mande uma DM
					para <a
						href="https://discord.com/users/241978119899185165"
						target="_blank"
						rel="noopener noreferrer">DoceAzedo</a
					> no Discord.
				</small>
			</p>
			<hr />
			<p class="is-flex is-justify-content-center" class:is-disabled={!isValidAmount}>
				<a
					href="/donate/checkout?amount={finalAmount}&username={username}"
					class="button checkout-btn is-primary"
				>
					Doar R$ {finalAmount} via MercadoPago
				</a>
			</p>
		{:else}
			<p class="notification is-warning is-light">
				Por favor,
				<a href="{env.PUBLIC_IDENTITY_URL}/auth/login">faça login</a> para visualizar a seção de doação.
			</p>
		{/if}
	</div>
</Card>

<style>
	.donation-options {
		display: flex;
		gap: 0.75rem;
		margin-bottom: 1rem;
	}

	.donation-options .button,
	.donation-options .field {
		flex-grow: 1;
	}

	.donation-options .button,
	.checkout-btn {
		transition: all 0.2s ease;
	}

	.donation-options .button.is-active {
		color: #fff;
		font-weight: 600;
		background-color: #2c9902;
		border-color: #2c9902;
	}

	.field .control {
		margin-bottom: 0;
	}

	.field .input {
		height: 100%;
		padding: 0.5rem 1rem;
		border-top-right-radius: 0.75rem;
		border-bottom-right-radius: 0.75rem;
	}

	.is-disabled {
		cursor: not-allowed;
	}

	.is-disabled .checkout-btn {
		opacity: 0.2;
		pointer-events: none;
	}
</style>

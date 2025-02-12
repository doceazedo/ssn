<script lang="ts">
	import { slide } from 'svelte/transition';
	import { quintOut } from 'svelte/easing';
	import { page } from '$app/stores';
	import { DiscordIcon, Input, Separator } from 'ssnkit';
	import { discordOauthUrl } from '$lib/helpers';
	import { Captcha } from '$lib/captcha';

	type Field = {
		label: string;
		name: string;
		value: string;
		type?: string;
		warning?: string;
	};

	type Link = {
		label: string;
		href: string;
	};

	export let fields: Field[] = [];
	export let links: Link[] = [];
	export let isLoading = false;
	export let error = '';
	export let success = '';
	export let discordLabel = '';
	export let submitLabel: string;
	export let hasCaptcha = false;
	export let captchaToken = '';

	const redirect = $page.url.searchParams.get('redirect');
	const redirectParams = redirect ? `?redirect=${redirect}` : '';
</script>

<form class="form" class:is-loading={isLoading} on:submit|preventDefault>
	{#if discordLabel}
		<a
			href={discordOauthUrl($page.url.searchParams.get('gk'))}
			class="button is-link is-light is-fullwidth"
		>
			<DiscordIcon />
			{discordLabel}
		</a>
		<Separator />
	{/if}
	{#each fields as field}
		<div class="field">
			<Input
				name={field.name}
				label={field.label}
				type={field.type}
				disabled={isLoading}
				bind:value={field.value}
			/>
			{#if field.warning}
				<div class="notification is-warning is-light">
					{@html field.warning}
				</div>
			{/if}
		</div>
	{/each}

	{#if hasCaptcha}
		<div class="field">
			<Captcha bind:token={captchaToken} />
		</div>
	{/if}

	{#if error}
		<div
			class="notification is-danger is-light"
			transition:slide={{ duration: 200, easing: quintOut }}
		>
			{error}
		</div>
	{/if}

	{#if success}
		<div
			class="notification is-success is-light"
			transition:slide={{ duration: 200, easing: quintOut }}
		>
			{success}
		</div>
	{/if}

	<button type="submit" class="button is-primary is-fullwidth" class:is-loading={isLoading}>
		{submitLabel}
	</button>
</form>

<div class="mt-auto">
	{#each links as link}
		<p><a class="has-text-grey" href={link.href + redirectParams}>{link.label}</a></p>
	{/each}
</div>

<style lang="sass">
  .form
    transition: all .2s ease

    &.is-loading
      opacity: .75
      pointer-events: none

  .notification.is-warning
    font-size: 14px
    margin-top: 0.5rem
    padding: 0.75rem 1rem
</style>

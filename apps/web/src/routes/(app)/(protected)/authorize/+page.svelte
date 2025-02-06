<script lang="ts">
	import Header from '$lib/components/Header.svelte';
	import { Button } from '$lib/components/ui/button';
	import { ArrowLeft, Check, CheckCircle, LockOpen } from 'phosphor-svelte';
	import { Checkbox } from '$lib/components/ui/checkbox';
	import { Label } from '$lib/components/ui/label';
	import * as m from '$lib/paraglide/messages.js';
	import { enhance } from '$app/forms';
	import { page } from '$app/state';
	import { browser } from '$app/environment';

	let { data, form } = $props();

	let loading = $state(false);
</script>

<Header />

{#if form?.success}
	<div class="mx-auto my-28 flex w-full max-w-lg flex-col items-center text-center">
		<span class="mb-4 flex size-16 items-center justify-center rounded-full bg-emerald-500/30">
			<Check class="size-9 text-emerald-500" />
		</span>
		<h1 class="mb-2 text-3xl font-bold">
			{m.all_set()}
		</h1>
		<p class="text-foreground/80 [&_span]:text-foreground mb-12 [&_span]:font-medium">
			{m.authorized_successfully()}
		</p>
		<Button variant="secondary" href="/">
			<ArrowLeft size={20} />
			{m.return_to_homepage()}
		</Button>
	</div>
{:else}
	<form
		method="POST"
		action="?/allow"
		use:enhance
		class="mx-auto flex w-full max-w-lg flex-col items-center gap-8 text-center"
		onsubmit={() => (loading = true)}
	>
		<input type="hidden" name="flowCode" value={browser && page.url.searchParams.get('flow')} />
		<h1 class="text-3xl font-bold">
			{m.authorize_login_title()}
		</h1>
		<p class="text-foreground/80 [&_span]:text-foreground [&_span]:font-medium">
			{@html m.authorize_login_text({
				username: data.username,
				region: data.region
			})}
		</p>
		<div class="flex gap-2">
			<Button variant="secondary" href="/account" aria-disabled={loading}>
				{m.cancel()}
			</Button>
			<Button type="submit" {loading}>
				{#if !loading}
					<LockOpen size={20} />
				{/if}
				{m.authorize()}
			</Button>
		</div>
		<hr class="w-full" />
		<div class="flex w-full flex-col gap-2">
			<div class="flex items-center gap-2">
				<Checkbox id="alwaysAllowIp" name="alwaysAllowIp" checked />
				<Label for="alwaysAllowIp" class="cursor-pointer text-sm font-normal leading-none">
					{m.always_allow_ip()}
				</Label>
			</div>
			<div class="flex items-center gap-2">
				<Checkbox id="allowWildcard" name="allowWildcard" checked />
				<Label for="allowWildcard" class="cursor-pointer text-sm font-normal leading-none">
					{m.allow_wildcard()}
				</Label>
			</div>
		</div>
	</form>
{/if}

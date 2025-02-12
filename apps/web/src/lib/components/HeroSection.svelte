<script lang="ts">
	import DiscordIcon from '$lib/components/icons/DiscordIcon.svelte';
	import { Button } from '$lib/components/ui/button';
	import { Check, Copy } from 'phosphor-svelte';
	import * as m from '$lib/paraglide/messages.js';
	import { getServerStatus, type ServerStatus } from '$lib/api/mcstatus';
	import { onDestroy, onMount } from 'svelte';
	import { DISCORD_INVITE, SERVER_IP, SERVER_VERSION } from '$lib/constants';
	import { copyToClipboard, toast } from '$lib/utils';
	import { scale } from 'svelte/transition';
	import { quintInOut } from 'svelte/easing';

	let status = $state<ServerStatus | null>(null);
	let statusInterval: ReturnType<typeof setInterval>;
	let copiedAt = $state<Date | null>(null);

	const copyIp = () => {
		copyToClipboard(SERVER_IP);
		toast.success(m.copied_ip());

		const now = new Date();
		copiedAt = now;
		setTimeout(() => {
			if (copiedAt !== now) return;
			copiedAt = null;
		}, 1000);
	};

	onMount(async () => {
		status = await getServerStatus();
		statusInterval = setInterval(async () => {
			status = await getServerStatus();
		}, 10000);
	});

	onDestroy(() => {
		clearInterval(statusInterval);
	});
</script>

<div
	class="absolute left-0 top-0 -z-10 h-2/3 w-full bg-cover bg-center lg:h-full"
	style="background-image:url(/img/trans-bee.webp)"
>
	<div class="from-background to-background/80 size-full bg-gradient-to-t"></div>
</div>

<header class="flex flex-col items-center px-8 py-28 md:px-0">
	<img
		src="/img/ssn-made-in-brasil.png"
		class="mb-8 h-20 md:h-28"
		alt="SSN.gg - Made in Brasil"
		style="user-select: none; user-drag: none"
		draggable="false"
	/>

	<p
		class="text-foreground/80 [&_span]:text-foreground mb-14 text-center text-lg font-light leading-snug md:mb-28"
	>
		{@html m.hero_slogan()}
		<br class="hidden md:block" />
		{@html m.hero_est()}
	</p>

	<div class="flex flex-col items-center gap-4 md:flex-row">
		<button
			class="group relative flex h-14 transition-all hover:-translate-y-0.5"
			onclick={copyIp}
			data-umami-event="Copy IP"
		>
			<span
				class="group-hover:bg-primary/90 bg-primary flex size-14 items-center justify-center rounded-l-lg border border-white/30 transition-all"
			>
				{#if copiedAt}
					<span
						class="absolute"
						transition:scale={{ duration: 200, easing: quintInOut, start: 0.5, opacity: 0 }}
					>
						<Check size={24} />
					</span>
				{:else}
					<span
						class="absolute"
						transition:scale={{ duration: 200, easing: quintInOut, start: 0.5, opacity: 0 }}
					>
						<Copy size={24} />
					</span>
				{/if}
			</span>
			<span
				class="group-hover:bg-muted/90 bg-muted flex h-14 flex-col justify-center rounded-r-lg border border-white/5 px-3 text-left transition-all"
			>
				<p class="font-medium">IP: SSN.gg</p>
				<p class="text-muted-foreground text-xs">
					{#if status?.online || !status}
						{status ? status?.players?.online : 8} online
					{:else}
						Offline
					{/if}
					&bull; Java Edition {SERVER_VERSION}
				</p>
			</span>
		</button>

		<Button
			variant="discord"
			size="lg"
			href={DISCORD_INVITE}
			target="_blank"
			rel="noopener noreferrer"
			data-umami-event="Discord invite"
		>
			<DiscordIcon class="size-6" />
			{m.join_discord()}
		</Button>
	</div>
</header>

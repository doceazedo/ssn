<script lang="ts">
	import { BookOpenText, UsersThree } from 'phosphor-svelte';
	import SectionTitle from './SectionTitle.svelte';
	import * as m from '$lib/paraglide/messages.js';
	import DiscordIcon from './icons/DiscordIcon.svelte';
	import BlueskyIcon from './icons/BlueskyIcon.svelte';
	import { Button } from './ui/button';
	import GitHubIcon from './icons/GitHubIcon.svelte';
	import { DISCORD_INVITE } from '$lib/constants';
</script>

<section class="mx-auto flex w-full max-w-6xl flex-col gap-8 px-4 xl:px-0">
	<SectionTitle>
		<UsersThree />
		{m.links_section_title()}
	</SectionTitle>

	<ul class="grid grid-cols-1 gap-4 md:grid-cols-2 md:gap-8 lg:grid-cols-4">
		<li class="flex flex-col items-center justify-center gap-4 rounded-lg border p-4">
			<div class="flex w-full items-center gap-4 md:flex-col md:gap-2">
				<span
					class="flex size-16 flex-shrink-0 items-center justify-center rounded-full bg-[#5865F230]"
				>
					<DiscordIcon class="size-8 text-[#5865F2]" />
				</span>
				<div class="flex flex-col md:items-center md:text-center">
					<h2 class="text-lg font-medium">Discord</h2>
					<p class="text-foreground/80">{m.links_section_discord()}</p>
				</div>
			</div>
			<Button
				variant="secondary"
				class="w-full"
				href={DISCORD_INVITE}
				target="_blank"
				rel="noopener noreferrer"
			>
				{m.join_discord_server()}
			</Button>
		</li>

		<li class="flex flex-col items-center justify-center gap-4 rounded-lg border p-4">
			<div class="flex w-full items-center gap-4 md:flex-col md:gap-2">
				<span
					class="flex size-16 flex-shrink-0 items-center justify-center rounded-full bg-[#0285FF30]"
				>
					<BlueskyIcon class="size-8 text-[#0285FF]" />
				</span>
				<div class="flex flex-col md:items-center md:text-center">
					<h2 class="text-lg font-medium">Bluesky</h2>
					<p class="text-foreground/80">{m.links_section_bluesky()}</p>
				</div>
			</div>
			<Button
				variant="secondary"
				class="w-full"
				href="https://bsky.app/profile/ssn.gg"
				target="_blank"
			>
				{m.follow()}
			</Button>
		</li>

		<li class="flex flex-col items-center justify-center gap-4 rounded-lg border p-4">
			<div class="flex w-full items-center gap-4 md:flex-col md:gap-2">
				<span
					class="bg-primary/30 flex size-16 flex-shrink-0 items-center justify-center rounded-full"
				>
					<BookOpenText class="text-primary size-8" />
				</span>
				<div class="flex flex-col md:items-center md:text-center">
					<h2 class="text-lg font-medium">Wiki</h2>
					<p class="text-foreground/80">{m.links_section_wiki()}</p>
				</div>
			</div>
			<Button variant="secondary" class="w-full" disabled>{m.access_wiki()}</Button>
		</li>

		<li class="flex flex-col items-center justify-center gap-4 rounded-lg border p-4">
			<div class="flex w-full items-center gap-4 md:flex-col md:gap-2">
				<span class="bg-muted flex size-16 flex-shrink-0 items-center justify-center rounded-full">
					<GitHubIcon class="size-8 text-white" />
				</span>
				<div class="flex flex-col md:items-center md:text-center">
					<h2 class="text-lg font-medium">GitHub</h2>
					<p class="text-foreground/80">{m.links_section_github()}</p>
				</div>
			</div>
			<Button
				variant="secondary"
				class="w-full"
				href="https://github.com/doceazedo/ssn"
				target="_blank"
			>
				{m.access_repo()}
			</Button>
		</li>
	</ul>
</section>

<script lang="ts">
	import { CheckCircle, Lock, UserPlus } from 'phosphor-svelte';
	import SectionTitle from './SectionTitle.svelte';
	import * as m from '$lib/paraglide/messages.js';
	import { Button } from './ui/button';
	import { page } from '$app/state';
	import { PRIMARY_USERNAME } from '$lib/stores';

	const features = [
		m.your_account_section_feature_login(),
		m.your_account_section_feature_customize(),
		m.your_account_section_feature_alts(),
		m.your_account_section_feature_settings()
	];
</script>

<section class="mx-auto mb-16 flex w-full max-w-6xl flex-col gap-8 px-4 xl:px-0">
	<SectionTitle>
		<Lock />
		{m.your_account_section_title()}
	</SectionTitle>
	<div class="flex flex-col gap-8 lg:flex-row lg:gap-14">
		<div
			class="bg-muted relative h-48 w-full overflow-hidden rounded-lg after:absolute after:left-0 after:top-0 after:size-full after:rounded-lg after:shadow-[inset_0_0_0_1px_#ffffff15] md:h-64 lg:h-auto"
		>
			<img src="/img/coffofin-bees.webp" alt="" class="size-full object-cover" loading="lazy" />
		</div>
		<div class="text-foreground/80 flex shrink-0 flex-col gap-8 lg:gap-14 lg:py-8">
			<div class="flex flex-col gap-8">
				<p class="max-w-[40ch]">{m.your_account_section_subtitle()}</p>
				<ul class="flex grid-cols-2 flex-col gap-x-8 gap-y-4 md:grid">
					{#each features as feature}
						<li class="flex items-center gap-2">
							<CheckCircle class="text-primary size-6" weight="fill" />
							{feature}
						</li>
					{/each}
				</ul>
			</div>
			<div class="flex items-center gap-2">
				{#if page.data.user}
					<Button href="/account" variant="secondary">
						<img
							src="https://mc-heads.net/avatar/{$PRIMARY_USERNAME?.name || 'MHF_Alex'}/64"
							alt=""
							class="size-5 rounded object-cover"
						/>
						{m.manage_your_account()}
					</Button>
				{:else}
					<Button href="/register">
						<UserPlus size={20} />
						{m.register()}
					</Button>
					<Button variant="link" href="/login">
						{m.already_registered()}
					</Button>
				{/if}
			</div>
		</div>
	</div>
</section>

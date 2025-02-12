<script lang="ts">
	import { browser } from '$app/environment';
	import { page } from '$app/state';
	import { i18n } from '$lib/i18n';
	import * as m from '$lib/paraglide/messages.js';
	import { languageTag } from '$lib/paraglide/runtime';
	import { cn } from '$lib/utils';
	import FlagBrIcon from './icons/FlagBrIcon.svelte';
	import FlagMxIcon from './icons/FlagMxIcon.svelte';
	import FlagUsIcon from './icons/FlagUsIcon.svelte';
	import { Button } from './ui/button';

	const href = $derived(i18n.route(page.url.pathname) + page.url.search);
</script>

<footer
	class="mx-auto mt-14 flex w-full max-w-6xl flex-col-reverse items-end gap-8 border-t px-4 py-8 md:mt-28 md:py-14 lg:flex-row lg:justify-between lg:gap-0 xl:px-0"
>
	<div
		class="flex w-full flex-col gap-2 md:items-center md:text-center lg:items-start lg:text-left"
	>
		<p
			class="text-foreground/80 [&_a]:text-foreground text-sm [&_a]:underline [&_a]:underline-offset-2"
		>
			{@html m.footer_license({
				date: new Date().getFullYear(),
				license: 'GPLv3',
				licenseUrl: 'https://github.com/doceazedo/ssn/blob/main/LICENSE'
			})}
			<br />
			{@html m.footer_credits({
				operator: 'DoceAzedo',
				operatorLink: 'https://github.com/doceazedo',
				contributorsLink: 'https://github.com/doceazedo/ssn/graphs/contributors'
			})}
		</p>
		<p class="text-muted-foreground max-w-[56ch] text-xs">
			{m.footer_unofficial_server()}
		</p>
	</div>

	<div class="flex w-full flex-col gap-1 md:items-center md:text-center lg:items-end lg:text-left">
		<p class="text-foreground/80 text-sm">{m.select_language()}</p>
		<div class="flex">
			<Button
				{href}
				hreflang="pt"
				variant="outline"
				class={cn('rounded-r-none', languageTag() === 'pt' && 'bg-muted')}
			>
				<FlagBrIcon class="size-5" />
				Português
			</Button>
			<Button
				{href}
				hreflang="es"
				variant="outline"
				class={cn('rounded-none border-x-0', languageTag() === 'es' && 'bg-muted')}
			>
				<FlagMxIcon class="size-5" />
				Español
			</Button>
			<Button
				{href}
				hreflang="en"
				variant="outline"
				class={cn('rounded-l-none', languageTag() === 'en' && 'bg-muted')}
			>
				<FlagUsIcon class="size-5" />
				English
			</Button>
		</div>
	</div>
</footer>

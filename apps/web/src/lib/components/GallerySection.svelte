<script lang="ts">
	import { Images } from 'phosphor-svelte';
	import SectionTitle from './SectionTitle.svelte';
	import * as m from '$lib/paraglide/messages.js';
	import { cn } from '$lib/utils';

	const SCREENSHOTS = [
		{
			src: '/img/solo-small-base.webp',
			alt: m.image_alt_solo_small_base(),
			class: 'col-span-2 md:col-span-6'
		},
		{
			src: '/img/deep-dark.webp',
			alt: m.image_alt_deep_dark(),
			class: 'md:col-span-6'
		},
		{
			src: '/img/christmas-event.webp',
			alt: m.image_alt_christmas_event(),
			class: 'md:col-span-4'
		},
		{
			src: '/img/withers.webp',
			alt: m.image_alt_withers(),
			class: 'col-span-2 md:col-span-4'
		},
		{
			src: '/img/halloween-jack.webp',
			alt: m.image_alt_halloween_jack(),
			class: 'col-span-2 md:col-span-4'
		}
	];
</script>

<section class="mx-auto mb-16 flex w-full max-w-6xl flex-col gap-8 px-4 xl:px-0">
	<SectionTitle>
		<Images />
		{m.gallery()}
	</SectionTitle>
	<div class="grid grid-cols-2 grid-rows-2 gap-4 md:grid-cols-12 md:gap-8">
		{#each SCREENSHOTS as image}
			<div
				class={cn(
					'bg-muted relative h-48 overflow-hidden rounded-lg after:absolute after:left-0 after:top-0 after:size-full after:rounded-lg after:shadow-[inset_0_0_0_1px_#ffffff15] md:h-64 lg:h-80',
					image.class
				)}
			>
				<img src={image.src} alt={image.alt} class="size-full object-cover" loading="lazy" />
			</div>
		{/each}
	</div>
</section>

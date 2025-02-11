<script lang="ts">
	import {
		CalendarDots,
		GithubLogo,
		Handshake,
		HandWaving,
		Heart,
		Prohibit,
		Wall
	} from 'phosphor-svelte';
	import SectionTitle from './SectionTitle.svelte';
	import * as m from '$lib/paraglide/messages.js';
	import { languageTag } from '$lib/paraglide/runtime';

	const getTimeSince = (date: Date, lang: 'pt' | 'en' | 'es') => {
		const now = new Date();
		const diffInMilliseconds = now.getTime() - date.getTime();

		let diffInYears = Math.floor(diffInMilliseconds / (1000 * 60 * 60 * 24 * 365));
		let diffInMonths = Math.ceil(
			(diffInMilliseconds % (1000 * 60 * 60 * 24 * 365)) / (1000 * 60 * 60 * 24 * 30)
		);
		if (diffInMonths >= 12) {
			diffInYears += 1;
			diffInMonths = 0;
		}

		let result = '';

		switch (lang) {
			case 'pt':
				if (diffInYears > 0) {
					result += `${diffInYears} ano${diffInYears > 1 ? 's' : ''}`;
				}
				if (diffInMonths > 0) {
					if (result) result += ' e ';
					result += `${diffInMonths} ${diffInMonths > 1 ? 'meses' : 'mês'}`;
				}
				break;
			case 'en':
				if (diffInYears > 0) {
					result += `${diffInYears} year${diffInYears > 1 ? 's' : ''}`;
				}
				if (diffInMonths > 0) {
					if (result) result += ' and ';
					result += `${diffInMonths} month${diffInMonths > 1 ? 's' : ''}`;
				}
				break;
			case 'es':
				if (diffInYears > 0) {
					result += `${diffInYears} año${diffInYears > 1 ? 's' : ''}`;
				}
				if (diffInMonths > 0) {
					if (result) result += ' y ';
					result += `${diffInMonths} mes${diffInMonths > 1 ? 'es' : ''}`;
				}
				break;
		}

		return result || '0 meses';
	};
</script>

<section class="mx-auto mb-16 flex w-full max-w-6xl flex-col px-4 xl:px-0">
	<SectionTitle>
		<HandWaving />
		{m.about_section_title()}
	</SectionTitle>
	<p class="text-foreground/80 mb-8 mt-8 max-w-[60ch] md:mb-14">
		{m.about_section_subtitle()}
	</p>
	<ul
		class="text-foreground/80 [&_span]:text-foreground mb-8 grid grid-cols-1 gap-8 md:mb-14 md:grid-cols-2 lg:grid-cols-3"
	>
		<li class="flex gap-2">
			<Prohibit size={20} class="text-primary shrink-0 translate-y-px" />
			<p>
				<span>{m.about_section_item_no_rules_title()}</span>
				{m.about_section_item_no_rules_text()}
			</p>
		</li>
		<li class="flex gap-2">
			<Wall size={20} weight="fill" class="text-primary shrink-0 translate-y-px" />
			<p>
				<span>{m.about_section_item_map_title()}</span>
				{m.about_section_item_map_text()}
			</p>
		</li>
		<li class="flex gap-2">
			<CalendarDots size={20} weight="fill" class="text-primary shrink-0 translate-y-px" />
			<p>
				<span
					>{m.about_section_item_history_title({
						years: new Date().getFullYear() - 2020
					})}</span
				>
				{m.about_section_item_history_text()}
			</p>
		</li>
		<li class="flex gap-2">
			<Heart size={20} weight="fill" class="text-primary shrink-0 translate-y-px" />
			<p>
				<span>{m.about_section_item_br_title()}</span>
				{m.about_section_item_br_text()}
			</p>
		</li>
		<li class="flex gap-2">
			<Handshake size={20} weight="fill" class="text-primary shrink-0 translate-y-px" />
			<p>
				<span>{m.about_section_item_community_title()}</span>
				{m.about_section_item_community_text()}
			</p>
		</li>
		<li class="flex gap-2">
			<GithubLogo size={20} weight="fill" class="text-primary shrink-0 translate-y-px" />
			<p>
				<span>{m.about_section_item_open_source_title()}</span>
				{m.about_section_item_open_source_text()}
			</p>
		</li>
	</ul>
	<ul class="grid grid-cols-2 gap-4 md:grid-cols-4 md:gap-0">
		<li class="flex flex-col justify-center border-l px-4 py-2">
			<p class="text-xl lg:text-2xl">
				{new Intl.DateTimeFormat(languageTag(), {
					dateStyle: languageTag() === 'pt' ? 'short' : 'medium'
				}).format(new Date('2020-03-21'))}
			</p>
			<p class="text-foreground/80 text-xs lg:text-sm">
				{m.about_section_est_date()}
			</p>
		</li>
		<li class="flex flex-col justify-center border-l px-4 py-2">
			<p class="text-xl lg:text-2xl">{getTimeSince(new Date('2023-01-13'), languageTag())}</p>
			<p class="text-foreground/80 text-xs lg:text-sm">
				{m.about_section_map_age()}
			</p>
		</li>
		<li class="flex flex-col justify-center border-l px-4 py-2">
			<p class="text-xl lg:text-2xl">280 GB</p>
			<p class="text-foreground/80 text-xs lg:text-sm">
				{m.about_section_map_size()}
			</p>
		</li>
		<li class="flex flex-col justify-center border-l px-4 py-2">
			<p class="text-xl lg:text-2xl">{languageTag() === 'en' ? '28,000' : '28.000'}+</p>
			<p class="text-foreground/80 text-xs lg:text-sm">
				{m.about_section_unique_players()}
			</p>
		</li>
	</ul>
</section>

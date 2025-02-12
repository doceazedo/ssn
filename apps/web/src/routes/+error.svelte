<script lang="ts">
	import { page } from '$app/state';
	import Footer from '$lib/components/Footer.svelte';
	import Header from '$lib/components/Header.svelte';
	import { Button } from '$lib/components/ui/button';
	import { ArrowLeft } from 'phosphor-svelte';
	import * as m from '$lib/paraglide/messages.js';
</script>

<Header />

<div class="mx-auto my-28 flex w-full max-w-6xl flex-col gap-4">
	<p class="text-primary">{page.status}</p>
	<h1 class="text-5xl font-bold">
		{m.error_page_title()}
	</h1>
	<p class="text-foreground/80 [&_span]:text-foreground mb-8 max-w-xl">
		{@html page.error?.message || m.no_error_message()}
	</p>
	<Button class="w-fit" href="/">
		<ArrowLeft size={20} />
		{m.return_to_homepage()}
	</Button>
</div>

<Footer />

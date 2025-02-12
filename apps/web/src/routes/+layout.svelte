<script lang="ts">
	import { i18n } from "$lib/i18n";
	import { ParaglideJS } from "@inlang/paraglide-sveltekit";
	import { Toaster } from 'svelte-french-toast';
	import { onMount } from "svelte";
	import '../app.css';
	import { page } from "$app/state";
	import { pb } from "$lib/pocketbase";
	import { PRIMARY_USERNAME, USER } from "$lib/stores";
	import * as m from '$lib/paraglide/messages.js';

	let { children } = $props();

	onMount(async () => {
		$USER = page.data.user;

		if (!$USER?.primaryUsername) return;
		try {
			$PRIMARY_USERNAME = await pb.collection('usernames').getOne($USER.primaryUsername);
		} catch (_error) {}
	});

	
</script>

<svelte:head>
	<title>SSN.gg - {m.seo_title()}</title>
	<meta name="title" content="SSN.gg - {m.seo_title()}" />
	<meta name="description" content={m.seo_description()} />

	<meta property="og:type" content="website" />
	<meta property="og:url" content="https://ssn.gg" />
	<meta property="og:title" content="SSN.gg - {m.seo_title()}" />
	<meta property="og:description" content={m.seo_description()} />
	<meta property="og:image" content="https://ssn.gg/img/thumbnail.jpg" />

	<meta property="twitter:card" content="summary_large_image" />
	<meta property="twitter:url" content="https://ssn.gg" />
	<meta property="twitter:title" content="SSN.gg - {m.seo_title()}" />
	<meta property="twitter:description" content={m.seo_description()} />
	<meta property="twitter:image" content="https://ssn.gg/img/thumbnail.jpg" />
</svelte:head>

<ParaglideJS {i18n}>
	{@render children()}
	<Toaster />
</ParaglideJS>

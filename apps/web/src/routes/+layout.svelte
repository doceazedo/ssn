<script lang="ts">
	import { i18n } from "$lib/i18n";
	import { ParaglideJS } from "@inlang/paraglide-sveltekit";
	import { Toaster } from 'svelte-french-toast';
	import { onMount } from "svelte";
	import '../app.css';
	import { page } from "$app/state";
	import { pb } from "$lib/pocketbase";
	import { PRIMARY_USERNAME, USER } from "$lib/stores";

	let { children } = $props();

	onMount(async () => {
		$USER = page.data.user;

		if (!$USER?.primaryUsername) return;
		try {
			$PRIMARY_USERNAME = await pb.collection('usernames').getOne($USER.primaryUsername);
		} catch (_error) {}
	});
</script>

<ParaglideJS {i18n}>
	{@render children()}
	<Toaster />
</ParaglideJS>

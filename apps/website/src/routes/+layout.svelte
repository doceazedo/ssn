<script lang="ts">
	import 'ssnkit/styles';
	import { Layout } from 'ssnkit';
	import { page } from '$app/stores';
	import { env } from '$env/dynamic/public';
	import { IDENTITY } from '$lib/auth';
	import { CURRENT_URL } from '$lib/stores';
	import type { LayoutServerData } from './$types';

	export let data: LayoutServerData;

	$: currentUrl = $page.url.href.endsWith('/') ? $page.url.href.slice(0, -1) : $page.url.href;
	$CURRENT_URL = currentUrl;

	$IDENTITY = data.identity || null;
</script>

<Layout
	{currentUrl}
	websiteBaseUrl={env.PUBLIC_WEBSITE_URL || ''}
	identityBaseUrl={env.PUBLIC_IDENTITY_URL || ''}
	identity={$IDENTITY}
>
	<slot />
</Layout>

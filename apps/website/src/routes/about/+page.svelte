<script lang="ts">
	import { ContentWithSidebar, Metadata, PageTitle } from 'ssnkit';
	import { CURRENT_URL } from '$lib/stores';
	import { Error } from 'ssnkit';
</script>

<Metadata currentUrl={$CURRENT_URL} pageTitle="Sobre o SSN" />

<PageTitle title="Sobre o SSN" class="mb-6" />

<ContentWithSidebar>
	<Error code="Erro 503" message="Essa página está indisponível" />
</ContentWithSidebar>

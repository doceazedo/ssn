<script lang="ts">
	import { ContentWithSidebar, GemIcon, Metadata, PageTitle } from 'ssnkit';
	import { CURRENT_URL } from '$lib/stores';
	import DonateWizard from '$lib/components/donate/DonateWizard.svelte';
	import { IDENTITY } from '$lib/auth';
	import Card from 'ssnkit/src/lib/components/card.svelte';
</script>

<Metadata currentUrl={$CURRENT_URL} pageTitle="Apoiar" />

<PageTitle title="Apoiar" class="mb-6" />

<ContentWithSidebar>
	<Card title="Benefícios" icon={GemIcon}>
		<div class="content">
			<p>
				Doar para o SSN é totalmente opcional, todo mundo é bem-vindo a jogar, apoiando o servidor
				ou não. Por isso, as doações <b>não vão te dar vantagens</b> no jogo, mas você vai ganhar alguns
				benefícios que não interferem no jeito de jogar:
			</p>

			<ul>
				<li>
					<b>Prioridade na fila*:</b> você terá prioridade para entrar quando o servidor estiver cheio;
				</li>
				<li>
					<b>Falar colorido no chat:</b> você vai poder usar códigos para deixar suas mensagens coloridas;
				</li>
				<li>
					<b>Cor do nome personalizada:</b> escolha a cor que seu nome aparece no chat e no TAB*;
				</li>
				<li>
					<b>Personalizar skin:</b> altere sua skin pelo seu painel*, mesmo sem Minecraft original;
				</li>
				<li>
					<b>Personalizar perfil:</b> você poderá usar HTML para personalizar
					<a
						href="/u/{$IDENTITY?.primaryUsername || 'DoceAzedo'}"
						target="_blank"
						rel="noopener noreferrer">seu perfil público</a
					>
					do SSN.
				</li>
				<li><b>Cargo no Discord:</b> ganhe um cargo exclusivo no nosso Discord;</li>
			</ul>

			<p>
				<small>* Disponível em breve</small>
			</p>
		</div>
	</Card>

	<DonateWizard />
</ContentWithSidebar>

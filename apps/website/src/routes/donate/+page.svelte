<script lang="ts">
	import {
		ContentWithSidebar,
		Error,
		GemIcon,
		HeartHandshakeIcon,
		Metadata,
		PageTitle
	} from 'ssnkit';
	import { dev } from '$app/environment';
	import { page } from '$app/stores';
	import { CURRENT_URL } from '$lib/stores';
	import DonateWizard from '$lib/components/donate/DonateWizard.svelte';
	import { IDENTITY } from '$lib/auth';
	import Card from 'ssnkit/src/lib/components/card.svelte';

	$: isPreview = $page.url.searchParams.get('preview') !== null;
</script>

<Metadata currentUrl={$CURRENT_URL} pageTitle="Apoiar" />

<PageTitle title="Apoiar" class="mb-6" />

<ContentWithSidebar>
	{#if dev || isPreview}
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
						do SSN;
					</li>
					<li><b>Cargo no Discord:</b> ganhe um cargo exclusivo no nosso Discord.</li>
				</ul>

				<p>
					<small>* Disponível em breve</small>
				</p>
			</div>
		</Card>

		<DonateWizard />

		<Card title="Pra onde vai o dinheiro?" icon={HeartHandshakeIcon}>
			<div class="content">
				<p>
					No momento, o servidor não gera gastos. A hospedagem atual (8 vCPU, 24GB RAM, 100GB de
					armazenamento) é fornecida pela OCP gratuitamente.
				</p>
			</div>
		</Card>

		<Card title="Outras formas de ajudar" icon={HeartHandshakeIcon}>
			<div class="content">
				<p>
					Existem muitas outras formas de ajudar o SSN a se manter de pé que não precisam de
					dinheiro, por exemplo:
				</p>
				<ul>
					<li>
						<b>Conte pros seus amigos:</b> a verdade é que o servidor não é nada sem as pessoas que jogam
						nele. Por isso, fale do SSN pros seus amigos e chame eles pra jogar com você! Assim, você
						ajuda a manter o servidor mais movimentado.
					</li>
					<li>
						<b>Boost no Discord:</b> nossa comunidade no Discord é o principal lugar onde eu e os
						jogadores conversamos sobre o SSN, e esse engajamento é muito importante pra manter o
						servidor vivo. Então, se tiver um boost sobrando, considere usar no servidor.
						<small>
							Depois,
							<a
								href="https://discord.com/users/241978119899185165"
								target="_blank"
								rel="noopener noreferrer"
							>
								me chama na DM
							</a>
							que eu te arrumo 15 dias de benefícios de doador pela generosidade!
						</small>
					</li>
				</ul>
			</div>
		</Card>
	{:else}
		<Error code="Erro 503" message="Essa página está indisponível" />
	{/if}
</ContentWithSidebar>

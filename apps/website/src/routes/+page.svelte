<script lang="ts">
	import { ContentWithSidebar, Card, CopyIpTag, Metadata } from 'ssnkit';
	import { Hand, LifeBuoy, Rocket, Swords } from 'lucide-svelte';
	import { GallerySlider } from '$lib/components/gallery';
	import ClientsTable from '$lib/components/clients-table.svelte';
	import { SERVER_STATUS } from '$lib/status';
	import { CURRENT_URL } from '$lib/stores';
</script>

<Metadata currentUrl={$CURRENT_URL} />

<ContentWithSidebar status={$SERVER_STATUS}>
	<GallerySlider />

	<Card title="Conheça o SSN.gg!" icon={Hand}>
		<div class="content">
			<p>
				O <b>SSN</b>, também conhecido como "servidor sem nome", é um dos poucos servidores de
				anarquia no Minecraft que ainda estão ativos no Brasil. A ideia é criar um ambiente sem
				regras e sem censura, onde a única preocupação da administração é manter o servidor no ar.
				Ele foi fundado em <b>21/03/2020</b>, mas o mapa atual só foi criado em <b>13/01/2023</b>.
			</p>
			<p>
				O servidor já foi fechado, reaberto e reiniciado várias vezes, e agora estamos na <b
					>quinta edição</b
				>, que a gente espera que seja a definitiva. Tudo o que tem aqui, desde as configurações até
				os plugins e sistemas, é de
				<a href="https://github.com/doceazedo/ssn" target="_blank">código-aberto</a>, o que
				significa que qualquer um pode verificar o código e contribuir para o SSN, ou até criar sua
				própria instância do servidor.
			</p>
		</div>
	</Card>

	<Card title="Como conectar" icon={LifeBuoy} id="tutorial">
		<div class="notification is-warning is-light">
			Se você não tem certeza de como baixar e jogar Minecraft, talvez o SSN não seja a melhor opção
			para começar a jogar.
		</div>
		<div class="content">
			<p>
				O SSN está disponível para o Minecraft <b>Java Edition</b> à partir da versão 1.9, sendo
				recomendado jogar na <b>versão 1.19.4</b>. Se você adquiriu o Minecraft, baixe o
				<a href="https://minecraft.net/pt-br/download" target="_blank">launcher oficial</a>
				no site do jogo. Caso você não possua uma cópia do jogo, recomendo o uso do
				<a href="https://tlaun.ch" target="_blank">TL Legacy</a>.
			</p>
			<ol>
				<li>Abra o Minecraft na versão 1.19.4</li>
				<li>Acesse o servidor pelo IP: <CopyIpTag /></li>
				<li>Registre-se com e-mail e senha ou via Discord no nosso site</li>
			</ol>
			<p>
				Se você está preocupado com sua segurança, recomendamos que <a href="/safety"
					>leia essa página</a
				>.
			</p>
			<p>
				Se você está procurando um launcher para gerenciar mods e contas de forma mais fácil, eu
				recomendo o <a href="https://prismlauncher.org" target="_blank">Prism Launcher</a>. E se
				quiser dicas de mods e clients, dá uma olhada na seção abaixo.
			</p>
		</div>
	</Card>

	<Card title="Clients e mods" icon={Swords} id="clients">
		<div class="notification is-danger is-light">
			<b
				>Nunca baixe arquivos que você não conhece, mesmo que de pessoas que você confia! Baixe mods
				apenas da fonte oficial e faça bom uso do bom senso.</b
			>
		</div>
		<div class="content">
			<p>
				A maioria das pessoas no servidor usam clients modificados, e tem várias opções por aí. Eu
				vou deixar uma lista com algumas <b>sugestões pessoais</b>, mas que fique claro que eu não
				me responsabilizo por problemas que você possa ter com eles e não ofereço suporte. Você terá
				que descobrir como configurar cada módulo para funcionar no SSN por conta própria.
			</p>
			<p>
				Existem clients com módulos focados em utilidade e PVP. Se você é novo no jogo, mods
				utilitários provavelmente farão mais sentido para você. Algumas opções incluem:
			</p>
			<p>
				<ClientsTable />
			</p>
			<p>
				Eu recomendo experimentar alguns desses clients e ver qual você gosta mais. Alguns até
				funcionam bem quando usados em conjunto se você quiser usar mais de um ao mesmo tempo.
			</p>
			<p>
				Agora, uma coisa importante: <b>qualquer client pode ser perigoso</b>. Por isso, é melhor
				minimizar o risco de infectar seu computador baixando os arquivos apenas da fonte oficial,
				usando mods populares e, se possível, auditar o código e fazer sua própria build. Eu também
				recomendo evitar usar clients crackeados ou vazados, porque pode ser difícil para um jogador
				inexperiente distinguir um mod seguro de um infectado.
			</p>
		</div>
	</Card>

	<Card title="Próximos passos" icon={Rocket}>
		<div class="content">
			<p>
				Então, você já baixou alguns mods e se conectou no SSN? Show! Aqui vão algumas dicas do que
				fazer:
			</p>
			<ul>
				<li>
					<b>Faça uma viagem pelo Nether.</b> Se achar difícil conseguir recursos por perto do spawn
					por causa do caos, pegue algumas obsidianas e viaje pelo Nether (que é 8x mais rápido) e volte
					pro mundo normal quando estiver cansado.
				</li>
				<li>
					<b>Construa sua base.</b> Comece a coletar recursos e estabeleça sua base numa área afastada.
				</li>
				<li>
					<b>Participe de um clã.</b> Jogar no SSN pode ser mais divertido com outras pessoas. Embora
					o servidor não tenha um sistema de clãs próprio, muitos jogadores se juntam com objetivos em
					comum.
				</li>
				<li>
					<b>Visite o spawn.</b> De vez em quando, volte pro spawn (coordenadas 0;0) e veja o que está
					acontecendo, encontre novos jogadores, cause o caos ou participe de uma briga de rua.
				</li>
				<li>
					<b>Junte-se a comunidades.</b> Acredite ou não, o SSN tem uma comunidade bem diversa,
					então é fácil encontrar outras pessoas com os mesmos interesses que você. Você pode
					começar participando do <a href="/discord" target="_blank">nosso Discord oficial</a>.
				</li>
				<li>
					<b>Deixe sua marca.</b> Faça e destrua grandes construções, promova eventos, se torne notável...
					não há limites do que você pode fazer no SSN.
				</li>
				<li>
					<b>Divirta-se!</b> Sem regras, o ambiente pode ficar tóxico facilmente, então lembre-se de
					levar o jogo com leveza e fazer o que te diverte! É isso que o servidor de anarquia é sobre.
				</li>
			</ul>
		</div>
	</Card>
</ContentWithSidebar>

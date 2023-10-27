<script lang="ts">
	import { ContentWithSidebar, Card, CopyIpTag, Metadata } from 'ssnkit';
	import { Hand, LifeBuoy, Swords } from 'lucide-svelte';
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
				O <b>SSN</b> — antigamente chamado de "servidor sem nome" — é um servidor de Minecraft
				brasileiro onde a única regra é não ter regras, e a administração só quer garantir que tudo
				funcione direitinho. Ele começou em <b>Março de 2020</b>, mas o mapa que a gente joga agora
				foi feito em Janeiro de 2023.
			</p>
			<p>
				Esse servidor já fechou e abriu várias vezes, e agora estamos na <b>quinta versão</b>, que
				eu espero que dure pra sempre. O legal é que tudo aqui, como as configurações, os plugins e
				as coisinhas extras, tem o
				<a href="https://github.com/doceazedo/ssn" target="_blank">código aberto</a>. Isso quer
				dizer que qualquer pessoa pode olhar como é feito e até ajudar a melhorar o SSN.
				<b>É como se o SSN fosse feito por todo mundo! ✨</b>
			</p>
			<a href="/about" class="button is-primary">Ler história</a>
		</div>
	</Card>

	<Card title="Como conectar" icon={LifeBuoy} id="tutorial">
		<div class="notification is-warning is-light">
			O SSN só funciona no <b>Minecraft Java Edition</b>. Ou seja, se você quiser jogar no celular,
			você vai precisar usar o
			<a href="https://pojavlauncherteam.github.io" target="_blank" rel="noopener noreferrer"
				>PojavLauncher</a
			>.
		</div>
		<div class="content">
			<p>
				Você pode jogar com qualquer versão da 1.9 pra cima, mas é melhor jogar na <b
					>versão 1.19.4</b
				>. Se você comprou o Minecraft, é só baixar o
				<a href="https://minecraft.net/pt-br/download" target="_blank">programa oficial</a>
				no site deles. Se não, pode usar o
				<a href="https://tlaun.ch" target="_blank">TL Legacy</a>.
			</p>
			<ol>
				<li>Abra o Minecraft e escolha a versão 1.19.4</li>
				<li>Adicione o servidor usando o IP <CopyIpTag /></li>
				<li>Se cadastre no site com seu e-mail e uma senha, ou usando seu Discord</li>
			</ol>
			<p>
				Se estiver preocupado com segurança, é bom dar uma lida <a href="/safety">nessa página</a>.
			</p>
		</div>
	</Card>

	<Card title="Clients e mods" icon={Swords} id="clients">
		<div class="notification is-danger is-light">
			<b>
				É muito importante não baixar coisas de pessoas desconhecidas, mesmo que você confie nelas.
				Só baixe mods do site oficial e use o bom senso.
			</b>
		</div>
		<div class="content">
			<p>
				Muita gente no servidor usa hacks para deixar o jogo mais interessante, mas eu não vou poder
				te ajudar se tiver algum problema com eles. Você vai ter que aprender a baixar e mexer neles
				sozinho, ou pedir ajuda pra alguém.
			</p>
			<p>
				<ClientsTable />
			</p>
			<p>
				Minha dica é experimentar alguns desses e ver qual você curte mais. Alguns até funcionam bem
				juntos se você quiser usar mais de um ao mesmo tempo.
			</p>
			<p>
				Mas olha, todos esses mods podem ser perigosos. Por isso, é melhor baixar só dos sites
				oficiais, usar os mods que bastante gente já usa e, se você souber como, conferir o código
				você mesmo. Evite mods que são "pirateados" ou vazados, porque pode ser difícil saber qual é
				seguro de verdade.
			</p>
		</div>
	</Card>
</ContentWithSidebar>

<style>
	.button {
		width: fit-content;
	}
</style>

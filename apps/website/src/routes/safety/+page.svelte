<script lang="ts">
	import { Card, ContentWithSidebar, Metadata, PageTitle } from 'ssnkit';
	import { FileCog, FormInput, Key, Lock, ShieldCheck } from 'lucide-svelte';
	import { CURRENT_URL } from '$lib/stores';
</script>

<Metadata currentUrl={$CURRENT_URL} pageTitle="Segurança" />

<PageTitle title="Segurança" class="mb-6" />

<ContentWithSidebar>
	<Card title="Autenticação" icon={Lock}>
		<div class="content">
			<p>
				O SSN não usa o sistema da Mojang quando alguém entra no servidor. Isso significa que
				qualquer um pode entrar, mesmo sem ter comprado o jogo. Por isso, precisamos de um jeito
				diferente de saber quem é quem e garantir que ninguém use a conta de outra pessoa.
			</p>
			<p>
				Nossa solução é entrar pelo site, que é mais seguro do que fazer login pelo chat, por
				exemplo. Além disso, você pode usar a mesma senha para todas as suas contas no SSN e só
				entrar uma vez, o que é mais fácil do que lembrar de várias senhas diferentes.
			</p>
		</div>
	</Card>

	<Card title="Login com Discord" icon={Key}>
		<div class="content">
			<p>
				Entrar com o Discord é ainda mais seguro do que usar um e-mail e senha, porque você não
				depende da segurança do SSN. O SSN <b>nunca</b> recebe sua senha do Discord, e funciona como
				qualquer login usando redes sociais que outros sites também têm. O SSN só recebe a mensagem do
				Discord que confirma que você é o dono da sua conta e que podemos te deixar passar.
			</p>

			<p>
				Agora, você precisará ter acesso à sua conta do Discord e proteger bem ela, por exemplo,
				ativando a autenticação de dois fatores na sua conta.
			</p>
		</div>
	</Card>

	<Card title="Autenticação de dois fatores" icon={FormInput}>
		<div class="content">
			<p>
				A autenticação de dois fatores (2FA ou MFA) é um código extra para acessar suas contas. Ela
				torna sua conta ainda mais difícil de ser hackeada, mesmo que alguém descubra sua senha.
			</p>
			<p>
				Com o 2FA ativado, quando você entrar na sua conta, além da sua senha, você também precisará
				digitar um código especial que é enviado para o seu celular. Esse código sempre muda, então
				é impossível de alguém adivinhar.
			</p>
			<p>
				O SSN ainda™ não tem o 2FA disponível. Mas mesmo assim, é importante ativar o 2FA no seu
				Discord e e-mail para proteger suas contas.
			</p>
		</div>
	</Card>

	<Card title="Modificações de terceiros" icon={FileCog}>
		<div class="content">
			<p>
				Mods podem adicionar funcionalidades e ferraments no seu jogo, e você pode usar quase
				qualquer um desses mods no SSN. Mas é importante escolher os certos para não ter problemas.
			</p>
			<ul>
				<li>
					<b>Use mods populares:</b> Procure mods que muitas pessoas usam e que são bem avaliados e baixe
					apenas da fonte oficial.
				</li>
				<li>
					<b>Se você sabe programar:</b> Verifique o código do mod antes de instalar. Faça sua própria
					build do mod para ter certeza de que é segura.
				</li>
				<li>
					<b>Cuidado com arquivos enviados por outras pessoas:</b> Nunca baixe arquivos de pessoas que
					você não conhece, eles podem ter vírus que podem danificar seu computador.
				</li>
			</ul>
		</div>
	</Card>

	<Card title="Proteja-se online" icon={ShieldCheck}>
		<div class="content">
			<p>
				Tenha sempre cuidado com o que você compartilha no SSN, não clique em links estranhos, não
				passe informações pessoais para ninguém e tome cuidado com quem você deixa ver suas redes
				sociais. Mesmo pessoas que parecem de confiança podem não ser tão legais assim lá no fundo.
			</p>
			<p>
				Lembre-se que você pode sempre ignorar pessoas que te provocam (use <code
					>/ignore &lt;nome&gt;</code
				> no chat), você não precisa responder com ofensas, pelo contrário, você poder ser legal com
				os outros :)
			</p>
			<p>
				O SSN pode ser um lugar legal se a atitude positiva partir de você e se você escolher bem
				com quem quer jogar! Lembre-se que é só um jogo, vai se divertir!
			</p>
		</div>
	</Card>
</ContentWithSidebar>

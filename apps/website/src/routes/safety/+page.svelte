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
				O SSN não tem contato com os servidores da Mojang, assim possibilitando que qualquer pessoa
				acesse o servidor, mesmo que ela não tenha adquirido uma cópia original do jogo. Isso
				significa que precisamos ter um sistema de autenticação próprio para garantir que somente
				usuários autorizados tenham acesso às suas respectivas contas.
			</p>
			<p>
				Um sistema de autenticação web também é mais seguro do que um sistema de login via chat
				convencional, pois os dados de login são transmitidos e armazenados de forma segura em vez
				de serem enviados via chat, o que pode ser interceptado por terceiros mal-intencionados.
				Além disso, nosso sistema de autenticação permite que os usuários usem a mesma senha para
				múltiplas contas, o que é mais conveniente do que ter que lembrar senhas diferentes para
				cada nome de usuário.
			</p>
			<p>
				Nosso sistema também nos permite ter um controle mais granular sobre quem pode se registrar
				e acessar o servidor.
			</p>
		</div>
	</Card>

	<Card title="Login com Discord" icon={Key}>
		<div class="content">
			<p>
				O registro via Discord usa a tecnologia oAuth 2.0, que é ainda mais seguro do que usar um
				sistema de login e senha convencional, pois você não precisa lembrar de mais uma senha e nem
				depende da segurança do SSN. O oAuth é um padrão aberto que permite que os usuários façam
				login em aplicativos e sites usando suas contas de redes sociais, no caso, o Discord. Isso
				significa que os usuários não precisam criar uma conta específica para o SSN, em vez disso,
				podem usar sua conta do Discord já existente.
			</p>
			<p>
				Para garantir que o login via Discord é realmente seguro, é importante verificar se você já
				está conectado à sua conta e que o site que está pedindo suas informações de login e senha
				são realmente o <b>discord.com</b>.
			</p>
			<p>
				Agora, é importante que você proteja a sua conta do Discord, para isso você deve usar uma
				senha forte e única e ativar o múltiplo fator de autenticação. Isso adiciona uma camada
				extra de segurança, exigindo que o usuário insira um código enviado para seu telefone ou
				e-mail além da senha ao fazer login.
			</p>
		</div>
	</Card>

	<Card title="Múltiplo fator de autenticação" icon={FormInput}>
		<div class="content">
			<p>
				Ativar o MFA, às vezes também referido como 2FA ou V2E, é a melhor maneira de aumentar a
				segurança da sua conta em qualquer site. O MFA exige que o usuário forneça mais de uma forma
				de autenticação, como uma senha e um código enviado para o telefone ou e-mail.
			</p>
			<p>
				Se você usa o Discord para fazer login no SSN, basta acessar as configurações da sua conta
				no Discord e habilitar o segundo fator de autenticação. Infelizmente, o sistema de
				autenticação do SSN ainda não tem suporte para MFA. No entanto, estamos trabalhando para
				implementar esse recurso em breve, para que você possa proteger ainda mais a sua conta.
			</p>
		</div>
	</Card>

	<Card title="Modificações de terceiros" icon={FileCog}>
		<div class="content">
			<p>
				Modificações de terceiros, também chamados de "mods" ou "clients", são programas adicionais
				que podem ser instalados no jogo para adicionar novas funcionalidades para auxiliar sua
				gameplay no SSN. Enquanto os mods podem ser muito úteis, é importante tomar alguns cuidados
				ao usá-los.
			</p>
			<p>
				Em primeiro lugar, é importante usar somente mods bem difundidos e populares, pois esses
				tendem a ser mais confiáveis e ter menos problemas. Além disso, é importante baixar os mods
				somente da fonte oficial, pois isso facilita que você obtenha uma versão segura e atualizada
				do mod.
			</p>
			<p>
				Se você tiver habilidades de programação, antes de instalar um mod, é uma boa ideia auditar
				o código para verificar se não há nenhum problema de segurança. O ideal ainda é que você
				faça sua própria build para evitar que ela tenha esses problemas.
			</p>
			<p>
				Por fim, é importante nunca baixar arquivos, especialmente JARs e EXEs, enviados por outras
				pessoas. Esses arquivos podem conter malwares que podem danificar seu computador ou roubar
				suas informações pessoais. Em vez disso, baixe sempre os mods da fonte oficial e verifique o
				código antes de instalá-los.
			</p>
		</div>
	</Card>

	<Card title="Proteja-se online" icon={ShieldCheck}>
		<div class="content">
			<p>
				Para proteger sua segurança online, é importante ser cauteloso com as informações que você
				compartilha na internet. Evite clicar em links que você não conhece e divulgar dados
				pessoais com outras pessoas, mesmo que você acredite confiar nelas. Também é uma boa ideia
				evitar compartilhar suas redes sociais com quem você não conhece bem, pois isso pode dar a
				elas acesso a mais informações sobre você.
			</p>
			<p>
				Se você estiver preocupado com sua segurança online, uma opção é apagar os rastros digitais
				que você deixa na internet. Isso inclui desativar contas de redes sociais que você não usa
				mais, privar perfis pessoais e excluir comentários e postagens antigas. Isso pode ajudar a
				proteger sua privacidade e segurança online.
			</p>
			<p>
				Outra coisa a levar em consideração é não levar chacota de outros usuários a sério. Na
				internet, algumas pessoas podem tentar se divertir às custas da reação negativa de outras,
				mas é importante não deixar que isso afete você. Em vez disso, tente ignorar e não retribuir
				esses comentários e, é claro, evite praticar esse mesmo comportamento com os outros. Ao
				seguir essas dicas, você pode se proteger e manter um ambiente mais positivo e respeitoso na
				internet.
			</p>
		</div>
	</Card>
</ContentWithSidebar>

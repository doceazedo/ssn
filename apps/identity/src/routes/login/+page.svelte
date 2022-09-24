<script>
  import { DiscordIcon, PageTitle, Input, Separator } from "ssnkit";
</script>

<PageTitle pretitle="Login" title="É um prazer te ver de novo." />

<div>
  <button class="button is-link is-light is-fullwidth">
    <DiscordIcon />
    Fazer login com Discord
  </button>
  <Separator />
  <div class="field">
    <Input label="E-mail ou nome de usuário" />
  </div>
  <div class="field">
    <Input label="Senha" type="password" />
  </div>
  <button class="button is-primary is-fullwidth">Login</button>
</div>

<div class="mt-auto">
  <p><a class="has-text-grey" href="/register">Criar uma conta</a></p>
  <p><a class="has-text-grey" href="/recover-password">Esqueceu sua senha?</a></p>
</div>

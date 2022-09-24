<script>
  import { DiscordIcon, PageTitle, Input, Separator } from "ssnkit";
</script>

<PageTitle pretitle="Registrar" title="Boas-vindas ao ssn.gg!" />

<div>
  <button class="button is-link is-light is-fullwidth">
    <DiscordIcon />
    Registrar com Discord
  </button>
  <Separator />
  <div class="field">
    <Input label="E-mail" />
  </div>
  <div class="field">
    <Input label="Nickname" />
  </div>
  <div class="field">
    <Input label="Senha" type="password" />
  </div>
  <div class="field">
    <Input label="Confirmar senha" type="password" />
  </div>
  <button class="button is-primary is-fullwidth">Registrar</button>
</div>

<div class="mt-auto">
  <p><a class="has-text-grey" href="/login">Já possui uma conta?</a></p>
</div>

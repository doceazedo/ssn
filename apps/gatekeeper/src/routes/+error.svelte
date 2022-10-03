<script>
  import { page } from '$app/stores';
  import { env } from "$env/dynamic/public";

  const identityBaseUrl = env.PUBLIC_IDENTITY_URL;
</script>

<h1 class="title">Erro {$page.status}</h1>
<div class="notification is-danger is-light">
  {$page.error.message}
</div>
{#if $page.status === 401 || $page.status === 403}
  <div class="buttons">
    <a class="button is-primary" href="{identityBaseUrl}/auth/login?redirect={$page.url.href}">
      Trocar de conta
    </a>
    <a class="button" href="/">
      Voltar ao site
    </a>
  </div>
{/if}
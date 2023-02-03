<script lang="ts">
	import { browser } from '$app/environment';
  import { env } from '$env/dynamic/public';
  import { IDENTITY } from '$lib/auth';
	import { getProfileByUsername, updateProfile } from '$lib/profile';
  import { Card, PageTitle, Switch, UsernamePicker } from 'ssnkit';
	import { ExternalLink, Upload } from 'ssnkit/icons';
	import type { ProfileWithLikeCount } from 'warehouse';

  export let data;

  let profile: ProfileWithLikeCount = data.profile;
  let usernames = $IDENTITY?.usernames || [];
  let currentUsername = usernames[0];

  const handleUsernameUpdate = async () => {
    profile = await getProfileByUsername(currentUsername);
  }
  $: currentUsername && browser && handleUsernameUpdate();

  let isLoading = false;
  const handleSubmit = async () => {
    if (isLoading) return;
    isLoading = true;

    await updateProfile(currentUsername, {
      showAlts: profile.showAlts,
      aboutMe: profile.aboutMe || ''
    });
    
    isLoading = false;
  }
</script>

<PageTitle pretitle="Geral" title="Meu perfil" class="mb-6" />

<div class="container">
  <div class="username-header">
    <UsernamePicker {usernames} bind:currentUsername />
    <a href="{env.PUBLIC_WEBSITE_URL}/u/{currentUsername}" target="_blank">
      Ver perfil
      <ExternalLink size={16} />
    </a>
  </div>

  <Card>
    <form class="form" on:submit|preventDefault={handleSubmit}>
      <div class="field is-horizontal">
        <div class="field-label">
          <label class="label" for="">Mostrar outras contas</label>
        </div>
        <div class="field-body">
          <div class="field">
            <Switch bind:checked={profile.showAlts} />
          </div>
        </div>
      </div>

      <div class="field is-horizontal">
        <div class="field-label">
          <label class="label" for="">Mostrar redes sociais</label>
        </div>
        <div class="field-body">
          <div class="field">
            <Switch />
            <p class="help">
              Para escolher quais redes sociais mostrar, acesse a página
              <a href="/dashboard/connections">Conexões</a>.
            </p>
          </div>
        </div>
      </div>

      <div class="field is-horizontal">
        <div class="field-label">
          <label class="label" for="">Mostrar online/offline</label>
        </div>
        <div class="field-body">
          <div class="field">
            <Switch />
          </div>
        </div>
      </div>
      
      <div class="field is-horizontal">
        <div class="field-label">
          <label class="label" for="">Sobre mim</label>
        </div>
        <div class="field-body">
          <div class="field">
            <div class="control">
              <textarea class="textarea" placeholder="Ainda não conhecemos {$IDENTITY?.usernames[0]} muito bem..." bind:value={profile.aboutMe} />
            </div>
            <p class="help">
              Você pode usar
              <a href="https://www.markdownguide.org/cheat-sheet" target="_blank">Markdown</a>
              ou HTML para estilizar o texto.
            </p>
          </div>
        </div>
      </div>
      
      <div class="field is-horizontal">
        <div class="field-label">
          <label class="label" for="">Banner</label>
        </div>
        <div class="field-body">
          <div class="field">
            <div class="control">
              <div class="file is-normal">
                <label class="file-label">
                  <input class="file-input" type="file" name="resume">
                  <span class="file-cta">
                    <span class="file-icon">
                      <Upload size={16} />
                    </span>
                    <span class="file-label">
                      Escolher imagem…
                    </span>
                  </span>
                </label>
              </div>
            </div>
            <p class="help">
              Formatos permitidos: JPEG, PNG, GIF. Tamanho máximo: 5MB. Tamanho recomendado: 1920x540.
            </p>
          </div>
        </div>
      </div>

      <div class="field is-horizontal">
        <div class="field-label" />
        <div class="field-body">
          <div class="field">
            <div class="control">
              <button class="button is-primary" disabled={isLoading} class:is-loading={isLoading}>
                Atualizar perfil
              </button>
            </div>
          </div>
        </div>
      </div>
    </form>
  </Card>
</div>

<style lang="sass">
  .container
    display: flex
    flex-direction: column
    gap: 1rem

  .username-header
    display: flex
    align-items: center
    justify-content: space-between

    a
      display: inline-flex
      align-items: center
      gap: .5rem

  @media screen and (min-width: 769px)
    .field-body
      flex-grow: 4
</style>
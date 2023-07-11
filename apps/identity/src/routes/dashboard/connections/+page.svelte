<script lang="ts">
	import { Card, PageTitle, Switch } from 'ssnkit';
	import { updatePublicConnection } from '$lib/profile';
	import { SERVICE_DETAILS, type ServiceProfile } from 'ssnkit/utils';
	import type { Service } from '@prisma/client';

	type Data = {
		services: {
			service: Service;
			isPublic: boolean;
			profile: ServiceProfile;
		}[];
	};

	export let data: Data;

	const toggleSocialVisibility = async (service: Service, isPublic: boolean) => {
		await updatePublicConnection({ service, isPublic });
	};
</script>

<PageTitle pretitle="Geral" title="Conexões" class="mb-6" />

<div class="connections">
	{#each data.services as connection}
		<div class="connection">
			<Card>
				<div class="connection-content">
					<figure
						class="connection-icon"
						style="--avatar: url({connection.profile.avatar}); --badge: url({SERVICE_DETAILS[
							connection.service
						].badge})"
					/>
					<h2 class="title">{SERVICE_DETAILS[connection.service].name}</h2>
					<p class="name">{connection.profile.username}</p>
					<Switch
						checked={connection.isPublic}
						on:change={(e) =>
							toggleSocialVisibility(connection.service, e?.target?.checked || false)}
					>
						Mostrar no perfil
					</Switch>
					<button class="button is-small is-danger">Desconectar</button>
				</div>
			</Card>
		</div>
	{/each}
</div>

<style lang="sass">
  .connections
    display: flex
    gap: 1rem

  .connection
    width: 33.3333%

    &-content
      display: flex
      flex-direction: column
      align-items: center

    &-icon
      position: relative
      width: 4rem
      height: 4rem
      margin-bottom: .5rem
      border-radius: 50%
      background-image: var(--avatar)
      background-position: center
      background-repeat: no-repeat
      background-size: cover

      &::before
        content: ''
        position: absolute
        width: 1.75rem
        height: 1.75rem
        right: -0.5rem
        bottom: 0
        border-radius: 50%
        border: .25rem solid #fff
        background-color: #eee
        background-image: var(--badge)
        background-position: center
        background-repeat: no-repeat
        background-size: cover

    .title
      font-size: 1.25rem
      text-align: center
      margin-bottom: 0

    .name
      font-size: .8rem
      margin-bottom: 1.5rem

    .button
      margin-top: 1.5rem
</style>

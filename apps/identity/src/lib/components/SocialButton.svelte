<script lang="ts">
	import { SERVICE_DETAILS, type ServiceProfile } from '$lib/utils';
	import type { Service } from '@prisma/client';

	export let service: Service;
	export let profile: ServiceProfile;
</script>

<a href={profile.url} target="_blank" rel="noopener noreferrer" class="button is-secondary">
	<img class="avatar" src={profile.avatar} alt="Avatar de {profile.username}" />
	<p class="info">
		<span class="service">
			<svelte:component this={SERVICE_DETAILS[service].icon} />
			{SERVICE_DETAILS[service].name}
		</span>
		{profile.username}
	</p>
</a>

<style lang="sass">
  .button
    height: unset
    padding: .5rem

  .avatar
    height: 3rem
    width: 3rem
    border-radius: .5rem

  .info
    text-align: left
    line-height: 1.25

    .service
      display: flex
      gap: .25rem
      font-size: .8rem
      opacity: .75

    :global(svg)
      height: 1rem
      width: 1rem
</style>

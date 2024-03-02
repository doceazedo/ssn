<script lang="ts">
	import dayjs from 'dayjs';
	import SvelteMarkdown from 'svelte-markdown';
	import DOMPurify from 'dompurify';
	import { Card, ContentWithAside, PageTitle } from 'ssnkit';
	import watermelonIconImg from 'ssnkit/assets/img/watermelon-icon.png';
	import { Heart } from 'lucide-svelte';
	import * as skinview3d from 'skinview3d';
	import { onMount } from 'svelte';
	import { browser } from '$app/environment';
	import { getProfileSocials, toggleProfileLike, type ProfileSocials } from '$lib/profile';
	import { SocialButton } from 'ssnkit';

	export let data;

	const siteName = 'SSN.gg';
	const pageTitle = `${data.user.name} | Perfil no ${siteName}`;
	const pageDescription = `Veja o perfil de ${data.user.name} no ${siteName} com data de registro, horas jogadas e mais!`;
	const avatarImage = `https://mc-heads.net/avatar/${data.user.name}/256`;

	let playermodel: HTMLCanvasElement;
	let likes: number = data.likes;
	let hasLiked: boolean = data.hasLiked;
	let socials: ProfileSocials = [];

	onMount(() => {
		if (!browser) return;

		const skinViewer = new skinview3d.SkinViewer({
			canvas: playermodel,
			width: 272,
			height: 360,
			skin: `https://mc-heads.net/skin/${data.user.name}`,
			zoom: 0.75,
			animation: new skinview3d.WalkingAnimation(),
			panorama: '/assets/img/panorama.jpg'
		});
		skinViewer.autoRotate = true;
		skinViewer.autoRotateSpeed = 0.75;
	});

	const getFullDate = (date: Date) => dayjs(date).format('DD/MM/YYYY');

	const formatJoinCount = (count = 0) => `${count} vez${count == 1 ? '' : 'es'}`;

	const formatDuration = (seconds = 0) => {
		if (seconds < 60) return `${seconds} segundos`;

		const hours = seconds / 3600;

		if (hours < 1) {
			const minutes = seconds / 60;
			return `${minutes.toFixed(0)} ${minutes == 1 ? 'minuto' : 'minutos'}`;
		}

		if (hours < 100) {
			return `${hours.toFixed(1)} ${hours == 1 ? 'hora' : 'horas'}`;
		}

		return `${Math.floor(hours)} ${hours == 1 ? 'hora' : 'horas'}`;
	};

	const stats = [
		{
			label: 'Jogando desde',
			value: data.user.firstJoin ? getFullDate(new Date(data.user.firstJoin)) : '-'
		},
		{
			label: 'Tempo de jogo',
			value: formatDuration(data.user.playedSeconds)
		},
		{
			label: 'Entrou no servidor',
			value: formatJoinCount(data.user.joinCount)
		}
	];

	const toggleLikeClientSidePrediction = () => {
		likes = hasLiked ? --likes : ++likes;
		hasLiked = !hasLiked;
	};

	let isLoadingLike = false;
	const toggleLike = async () => {
		if (isLoadingLike) return;
		isLoadingLike = true;
		toggleLikeClientSidePrediction();

		try {
			await toggleProfileLike(data.profileId);
		} catch (error) {
			// fix client side prediction
			toggleLikeClientSidePrediction();

			console.error('Could not like the profile', data.profileId);
			console.error(error);
		}

		isLoadingLike = false;
	};

	let cleanAboutMe: string;
	onMount(async () => {
		if (!browser) return;
		cleanAboutMe = DOMPurify.sanitize(`${data.aboutMe}`);

		socials = await getProfileSocials(data.user.name);
	});
</script>

<svelte:head>
	<title>{pageTitle}</title>
	<meta name="title" content={pageTitle} />
	<meta name="description" content={pageDescription} />
	<meta name="theme-color" content="#2c9902" />
	<link rel="icon" href={watermelonIconImg} />

	<meta name="og:title" content={pageTitle} />
	<meta name="og:site_name" content={siteName} />
	<meta name="og:description" content={pageDescription} />
	<meta name="og:image" content={avatarImage} />

	<meta name="twitter:title" content={pageTitle} />
	<meta name="twitter:description" content={pageDescription} />
	<meta name="twitter:image" content={avatarImage} />
	<meta name="twitter:card" content="summary" />
</svelte:head>

<PageTitle title={data.user.name} class="mb-6">
	<svelte:fragment slot="title">
		{#if data.isDonator}
			<span class="tag is-gold">Doador</span>
		{/if}
		{#if data.isOnline || true}
			<span class="beacon" />
		{/if}
	</svelte:fragment>

	<div class="like">
		{likes}
		<button
			class="like-button"
			class:is-liked={hasLiked}
			disabled={data.isOwner || isLoadingLike}
			on:click={toggleLike}
		>
			<Heart size={16} />
		</button>
	</div>
</PageTitle>

<ContentWithAside reverse>
	<div class="cards" slot="aside">
		<Card>
			<canvas class="playermodel" bind:this={playermodel} />
		</Card>
		<Card title="Outras contas">
			{#if data.alts.length}
				<ul class="usernames">
					{#each data.alts as alt}
						<li data-label={alt}>
							<a data-sveltekit-reload href="/u/{alt}">
								<img src="https://mc-heads.net/avatar/{alt}/80" alt="Avatar de {alt}" />
							</a>
						</li>
					{/each}
				</ul>
			{:else}
				<i>Esse usuário não tem outras contas.</i>
			{/if}
		</Card>
	</div>

	<div class="cards" slot="main">
		<div class="stats">
			{#each stats as stat}
				<Card>
					<div class="stat">
						<p class="stat-label">{stat.label}</p>
						<h1 class="title">{stat.value}</h1>
					</div>
				</Card>
			{/each}
		</div>
		<Card title="Sobre mim">
			<div class="content">
				{#if data.aboutMe}
					<SvelteMarkdown source={cleanAboutMe} />
				{:else}
					Ainda não conhecemos {data.user.name} muito bem...
				{/if}
			</div>
		</Card>
		{#if socials.length}
			<Card title="Redes sociais">
				<div class="buttons">
					{#each socials as social}
						{#if social}
							<SocialButton profile={social} />
						{/if}
					{/each}
				</div>
			</Card>
		{/if}
		{#if data.badges.length}
			<Card title="Emblemas">
				<ul class="badges">
					{#each data.badges as badge}
						<li class="badge" data-label={badge.label}>
							<img src="/assets/img/badges/{badge.slug}.png" alt={badge.label} />
						</li>
					{/each}
				</ul>
			</Card>
		{/if}
	</div>
</ContentWithAside>

<style lang="sass">
  @mixin tooltip
    position: relative
  
    &::before
      content: attr(data-label)
      position: absolute
      width: max-content
      bottom: -2rem
      padding: .25rem .5rem
      font-size: .8rem
      background-color: rgba(#000, .75)
      color: #fff
      border-radius: .25rem
      transition: all .2s ease
      z-index: 1
  
    &:not(:hover)::before
      transform: translateY(-.25rem)
      opacity: 0

  .cards
    display: flex
    flex-direction: column
    gap: 1.5rem

  .badges
    display: flex
    flex-wrap: wrap
    gap: 1rem

    .badge
      display: flex
      justify-content: center
      align-items: center
      width: 3rem
      height: 3rem
      border-radius: .5rem
      background-color: #f5f5f5
      @include tooltip

      img
        width: 30px
        height: 30px
        image-rendering: pixelated

  .stats
    display: flex
    gap: 1.5rem

    :global(.card)
      flex-grow: 1
      width: 33%

    .stat
      display: flex
      flex-direction: column
      justify-content: center
      align-items: center
      gap: .25rem
      width: 100%
      height: 100%
      padding: .5rem 0

      &-label
        font-size: .8rem

      .title
        font-size: 1.25rem

  .like
    display: flex
    align-items: center
    gap: .1rem

    &-button
      display: flex
      justify-content: center
      align-items: center
      width: 2rem
      height: 2rem
      background-color: transparent
      color: #4a4a4a
      border: none
      border-radius: 50%

      &:not(:disabled):hover
        background-color: #e6e7ea
        cursor: pointer

      &:disabled:hover
        cursor: not-allowed

      &.is-liked
        color: #f14668

        :global(svg)
          fill: #f14668
          
        &:not(:disabled):hover
          background-color: rgba(#f14668, .2)

  .playermodel
    border-radius: 1rem

  .usernames
    display: flex
    flex-wrap: wrap
    gap: 1rem

    a,
    li
      display: flex
      justify-content: center

    li
      @include tooltip

    img
      width: 2.5rem
      height: 2.5rem
      border-radius: .5rem
      image-rendering: pixelated

  :global(.page-title .title)
    display: flex
    align-items: center
    gap: .75rem

  .beacon
    display: flex
    width: .75rem
    height: .75rem
    background-color: #2c9902
    border-radius: 50%
</style>

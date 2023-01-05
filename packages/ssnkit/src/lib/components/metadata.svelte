<script lang="ts">
  import defaultOpenGraphImg from '../../../assets/img/og-image.jpg';
  import watermelonIconImg from '../../../assets/img/watermelon-icon.png';

  export let appTitle = 'SSN';
  export let pageTitle = '';
  export let description = 'O autêntico servidor brasileiro de anarquia no Minecraft';
  export let currentUrl = '';
  export let thumbnail = '';

  $: title = !!title ? `${appTitle} • ${pageTitle}` : appTitle;
  $: openGraphImage = thumbnail || defaultOpenGraphImg;
</script>

<svelte:head>
  <title>{title}</title>
  <meta name="title" content={title} />
  <meta name="description" content={description} />
  <meta name="theme-color" content="#2c9902" />
  <link rel="icon" href={watermelonIconImg} />

  <meta prefix="og: http://ogp.me/ns#" property="og:type" content="website" />
  <meta prefix="og: http://ogp.me/ns#" property="og:url" content={currentUrl} />
  <meta prefix="og: http://ogp.me/ns#" property="og:title" content={title} />
  <meta prefix="og: http://ogp.me/ns#" property="og:description" content={description} />
  <meta prefix="og: http://ogp.me/ns#" property="og:image" content={openGraphImage} />

  <meta property="twitter:card" content="summary_large_image" />
  <meta property="twitter:url" content={currentUrl} />
  <meta property="twitter:title" content={title} />
  <meta property="twitter:description" content={description} />
  <meta property="twitter:image" content={openGraphImage} />
</svelte:head>
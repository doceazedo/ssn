<script lang="ts">
  import { Github } from 'lucide-svelte';
  import { DiscordIcon, GitHubIcon, TwitterIcon } from '../icons';
  import { CopyIpTag } from '../elements';

  const socials = [
    {
      icon: DiscordIcon,
      href: 'https://discord.gg/DChTnVTuKp',
    },
    {
      icon: TwitterIcon,
      href: 'https://twitter.com/servidorsemnome',
    },
    {
      icon: GitHubIcon,
      href: 'https://github.com/doceazedo/ssn',
    },
  ];
</script>

<footer class="footer">
  <div class="footer-left">
    <h1>ssn.gg</h1>
    <p>DoceAzedo &copy; {new Date().getFullYear()}</p>
  </div>

  <div class="footer-center">
    <h1>Pronto para jogar?</h1>
    <CopyIpTag />
  </div>

  <div class="footer-right">
    {#each socials as social}
      <a href={social.href} target="_blank" class="button is-light is-small">
        <svelte:component this={social.icon} />
      </a>
    {/each}
  </div>
</footer>

<style lang="sass">
  .footer
    display: flex
    justify-content: space-between
    padding: 5rem 2.5rem
    background-color: #fff
    border-top: 1px solid #e6e7ea

    > *
      width: 33.33333%

    &-left
      display: flex
      flex-direction: column
      justify-content: center

      h1
        font-size: 1.5rem

    &-center
      text-align: center

      h1
        margin-bottom: .25rem
        font-size: 2rem
        letter-spacing: -1.25px

    &-right
      display: flex
      align-items: center
      justify-content: flex-end
      gap: .5rem

      .button
        width: 3rem
        height: 3rem
        padding: 0

        :global(svg)
          width: 1.5rem
          height: 1.5rem

    h1
      font-family: 'Space Grotesk', sans-serif
      font-weight: 700
      line-height: 1.25

  @media screen and (max-width: 768px)
    .footer
      flex-direction: column
      padding: 2rem 1rem
      gap: .5rem

      > *
        width: 100%

      &-left
        text-align: center

      &-center
        display: none

      &-right
        justify-content: center

        .button
          width: 2rem
          height: 2rem

          :global(svg)
            width: 1rem
            height: 1rem
</style>
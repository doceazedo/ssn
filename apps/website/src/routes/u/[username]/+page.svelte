<script lang="ts">
  import dayjs from 'dayjs';
  import { Card, PageTitle } from 'ssnkit';
  import { formatDuration } from 'ssnkit/utils';
  import type { PageServerData } from './$types';

  export let data: PageServerData;

  const getFullDate = (date: Date) =>
    dayjs(date).format('DD/MM/YYYY [às] HH:mm:ss');
</script>

<PageTitle title={data.user.name} class="mb-6" />

<div class="notification is-warning is-light">
  Os perfis públicos do SSN ainda são um projeto em andamento.
</div>

<div class="cards">
  <Card title="Sobre mim">
    <div class="content">
      <img src="https://api.mineatar.io/body/full/{data.user.name}" alt="" />
      {#if data.user.firstJoin}
        <ul>
          <li>Eu entrei pela primeira vez em <b>{getFullDate(data.user.firstJoin)}</b></li>
          <li>Até agora, eu joguei por <b>{formatDuration(data.user.playedSeconds)}</b></li>
          <li>E eu fiz login <b>{data.user.joinCount} vezes</b></li>
        </ul>
      {/if}
    </div>
  </Card>

  {#if data.badges.length}
    <Card title="Meus emblemas">
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

<style lang="sass">
  .cards
    display: flex
    flex-direction: column
    gap: 1.5rem

  .badges
    display: flex
    gap: 1rem

    .badge
      position: relative
      display: flex
      justify-content: center
      align-items: center
      width: 3rem
      height: 3rem
      border-radius: .5rem
      background-color: #f5f5f5

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

      &:not(:hover)::before
        transform: translateY(-.25rem)
        opacity: 0

      img
        width: 30px
        height: 30px
        image-rendering: pixelated
</style>
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

<Card>
  <div class="content">
    <img src="https://api.mineatar.io/body/full/{data.user.name}" alt="" />
    <ul>
      <li>Eu entrei pela primeira vez em <b>{getFullDate(data.user.firstJoin)}</b></li>
      <li>Até agora, eu joguei por <b>{formatDuration(data.user.playedSeconds)}</b></li>
      <li>E eu fiz login <b>{data.user.joinCount} vezes</b></li>
    </ul>
  </div>
</Card>
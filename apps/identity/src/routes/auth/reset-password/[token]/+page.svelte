<script lang="ts">
  import { PageTitle } from 'ssnkit';
  import { NewPasswordForm } from '$lib/forms';

  export let data: any;
</script>

<PageTitle pretitle="Redefinir senha" title="Criar nova senha" />
<p>Escolha uma nova senha para acessar a sua conta <b>{data?.identity?.primaryUsername}</b>:</p>
<NewPasswordForm token={data?.token} />

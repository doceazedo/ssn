<script lang="ts">
  import { resetPassword } from '$lib/auth';
  import { Form } from '.';

  export let token: string | null = null;

  let fields = [
    {
      label: 'Nova senha',
      name: 'password',
      type: 'password',
      value: ''
    },
    {
      label: 'Confirmar nova senha',
      name: 'confirm-password',
      type: 'password',
      value: ''
    },
  ];
  let captchaToken = '';

  let isLoading = false;
  let error = '';

  const handleSubmit = async () => {
    isLoading = true;
    error = '';

    const password = fields.find(field => field.name == 'password')!.value;
    const confirmPassword = fields.find(field => field.name == 'confirm-password')!.value;

    try {
      if (password != confirmPassword) throw Error('As senhas informadas não combinam');

      await resetPassword(password, `${token}`, captchaToken);
    } catch (e: any) {
      error = e.message;
    }

    isLoading = false;
  }
</script>

<Form
  bind:fields
  on:submit={handleSubmit}
  {isLoading}
  {error}
  hasCaptcha
  submitLabel="Redefinir senha"
  bind:captchaToken
/>

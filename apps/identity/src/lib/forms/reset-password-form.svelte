<script lang="ts">
  import { sendResetPasswordEmail } from '$lib/auth';
  import { Form } from '.';

  let fields = [
    {
      label: 'Endereço de e-mail',
      name: 'email',
      type: 'email',
      value: ''
    }
  ];
  let captchaToken = '';

  const links = [
    {
      label: 'Criar uma conta',
      href: '/auth/register'
    }
  ];

  let isLoading = false;
  let error = '';
  let success = '';

  const handleSubmit = async () => {
    isLoading = true;
    error = '';
    success = '';

    const email = fields.find(field => field.name == 'email')!.value;

    try {
      await sendResetPasswordEmail(email, captchaToken);
      success = 'Caso esse e-mail esteja registrado, você receberá um link de redefinição de senha';
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
  {success}
  {links}
  hasCaptcha
  submitLabel="Enviar e-mail"
  bind:captchaToken
/>

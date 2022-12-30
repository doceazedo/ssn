<script lang="ts">
  import { page } from '$app/stores';
  import { login } from '$lib/auth';
  import { Form } from '.';

  const redirect = $page.url.searchParams.get('redirect');

  let fields = [
    {
      label: 'E-mail ou nome de usuário',
      name: 'login',
      value: ''
    },
    {
      label: 'Senha',
      name: 'password',
      type: 'password',
      value: '',
    },
  ];
  let captchaToken = '';

  const links = [
    {
      label: 'Criar uma conta',
      href: '/auth/register'
    },
    {
      label: 'Esqueceu sua senha?',
      href: '/auth/recover-password'
    }
  ]

  let isLoading = false;
  let error = '';

  const handleSubmit = async () => {
    isLoading = true;
    error = '';

    const username = fields.find(field => field.name == 'login').value;
    const password = fields.find(field => field.name == 'password').value;

    try {
      await login(username, password, captchaToken, redirect);
    } catch (e) {
      isLoading = false;
      error = e.message;
    }
  }
</script>

<Form
  bind:fields
  on:submit={handleSubmit}
  {isLoading}
  {error}
  {links}
  hasCaptcha
  discordLabel="Fazer login com Discord"
  submitLabel="Login"
  bind:captchaToken
/>

<script lang="ts">
  import { Form } from '.';
  import { login } from '$lib/auth';

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

  const links = [
    {
      label: 'Criar uma conta',
      href: '/register'
    },
    {
      label: 'Esqueceu sua senha?',
      href: '/recover-password'
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
      await login(username, password);
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
  discordLabel="Fazer login com Discord"
  submitLabel="Login"
/>

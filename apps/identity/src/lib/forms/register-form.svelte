<script lang="ts">
  import { page } from '$app/stores';
  import { register } from "$lib/auth";
  import { Form } from '.';

  const redirect = $page.url.searchParams.get('redirect');

  let fields = [
    {
      label: 'Endereço de e-mail',
      name: 'email',
      value: ''
    },
    {
      label: 'Nome de usuário',
      name: 'username',
      value: ''
    },
    {
      label: 'Senha',
      name: 'password',
      type: 'password',
      value: ''
    },
    {
      label: 'Confirmar senha',
      name: 'confirm-password',
      type: 'password',
      value: ''
    },
  ];

  const links = [
    {
      label: 'Já possui uma conta?',
      href: '/auth/login'
    }
  ]

  let isLoading = false;
  let error = '';

  const handleSubmit = async () => {
    isLoading = true;
    error = '';

    const email = fields.find(field => field.name == 'email').value;
    const username = fields.find(field => field.name == 'username').value;
    const password = fields.find(field => field.name == 'password').value;
    const confirmPassword = fields.find(field => field.name == 'confirm-password').value;

    try {
      if (password != confirmPassword) throw Error('As senhas informadas não combinam');

      await register({
        email,
        username,
        password
      }, null, redirect);
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
  discordLabel="Registrar com Discord"
  submitLabel="Registrar"
/>

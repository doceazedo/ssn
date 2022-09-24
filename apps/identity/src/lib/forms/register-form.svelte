<script lang="ts">
  import { Form } from '.';

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
      href: '/login'
    }
  ]

  let isLoading = false;
  let error = '';

  const handleSubmit = () => {
    isLoading = true;
    error = '';
  }
</script>

<Form
  bind:fields
  on:submit={handleSubmit}
  {isLoading}
  {links}
  discordLabel="Registrar com Discord"
  submitLabel="Registrar"
/>

import type { Template } from '..';

const template = `
<div style="font-family: Arial, Helvetica, sans-serif; font-size: 16px;">
  <p style="font-size: 24px; font-weight: 700;">Senha alterada</p>
  <p>A senha da sua conta <b>{{username}}</b> no SSN acaba de ser redefinida! 🎉</p>
</div>
`;

export const PASSWORD_CHANGED_TEMPLATE = (username: string): Template => ({
  subject: 'Senha redefinida com sucesso',
  html: template.replace('{{username}}', username)
});

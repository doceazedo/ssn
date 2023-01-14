import type { Template } from '..';

const template = `
<div style="font-family: Arial, Helvetica, sans-serif; font-size: 16px;">
  <p style="font-size: 24px; font-weight: 700;">Redefinir senha</p>
  <p>Você solicitou a redefinição de senha da conta <b>{{username}}</b> no SSN. Para continuar, acesse o seguinte link: <a href="{{url}}">{{url}}</a></p>
  <p>Se você não solicitou a redefinição de senha, por favor, desconsidere esse e-mail.</p>
</div>
`;

export const RESET_PASSWORD_TEMPLATE = (username: string, resetUrl: string): Template => ({
  subject: 'Redefinir senha do SSN.gg',
  html: template
    .replace('{{username}}', username)
    .replaceAll('{{url}}', resetUrl)
});

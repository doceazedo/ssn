import sgMail from '@sendgrid/mail';
import { env } from "$env/dynamic/private";

export type Template = {
  subject: string;
  html: string;
}

export const sendEmail = async (to: string, template: Template) => {
  const apiKey = env.SENDGRID_API_KEY;
  const from = env.SENDGRID_FROM;
  if (!apiKey || !from) return;

  sgMail.setApiKey(apiKey);
  sgMail.send({
    to,
    from,
    subject: template.subject,
    html: template.html,
  });
}

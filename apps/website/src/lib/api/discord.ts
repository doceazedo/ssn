import { env } from '$env/dynamic/private';
import type { Donation } from '@prisma/client';

const ADMIN_PING = '<@241978119899185165>';

export const sendMercadoPagoLog = async (data: unknown) => {
	try {
		const resp = await fetch(`${env.DISCORD_WEBHOOK_PAYMENTS}`, {
			method: 'POST',
			headers: {
				'Content-Type': 'application/json'
			},
			body: JSON.stringify({
				content: [
					'🔄 **Payment webhook triggered:**',
					'```json',
					JSON.stringify(data, null, 2),
					'```'
				].join('\n')
			})
		});
		return resp.ok;
	} catch (error) {
		return false;
	}
};

export const sendDonationLog = async (donation: Donation, duration: string) => {
	try {
		const expiresAt = Math.floor(donation.expiresAt.getTime() / 1000);
		console.log(`${env.DISCORD_WEBHOOK_PAYMENTS}`);
		const resp = await fetch(`${env.DISCORD_WEBHOOK_PAYMENTS}`, {
			method: 'POST',
			headers: {
				'Content-Type': 'application/json'
			},
			body: JSON.stringify({
				content: [
					'✅ **Payment processed succesfully:**',
					'',
					`**Donator:** ${donation.ownerName}`,
					`**Amount:** R$ ${donation.amount}`,
					`**Duration:** ${duration} (expires <t:${expiresAt}:f>)`,
					`**Gateway:** ${donation.gateway}`,
					`**Payment ID:** ${donation.paymentId}`,
					'',
					ADMIN_PING
				].join('\n')
			})
		});
		return resp.ok;
	} catch (error) {
		return false;
	}
};

export const sendDonationError = async (data: unknown, paymentId: string) => {
	try {
		const resp = await fetch(`${env.DISCORD_WEBHOOK_PAYMENTS}`, {
			method: 'POST',
			headers: {
				'Content-Type': 'application/json'
			},
			body: JSON.stringify({
				content: [
					'❌ **Payment failed:**',
					'',
					`**Payment ID:** R$ ${paymentId}`,
					'**Webhook data:**',
					'```json',
					JSON.stringify(data, null, 2),
					'```',
					'',
					ADMIN_PING
				].join('\n')
			})
		});
		return resp.ok;
	} catch (error) {
		return false;
	}
};

export const sendDonationMessage = async (donation: Donation) => {
	try {
		const resp = await fetch(`${env.DISCORD_WEBHOOK_DONATIONS}`, {
			method: 'POST',
			headers: {
				'Content-Type': 'application/json'
			},
			body: JSON.stringify({
				content: 'Acabamos de receber uma doação!',
				embeds: [
					{
						title: `Obrigado, ${donation.ownerName} por doar R$ ${donation.amount} para o SSN! <:minecraftHeart:1060413140782825533>`,
						description:
							'Faça sua doação [clicando aqui](https://ssn.gg/donate) e ganhe recompensas!',
						color: 4769678
					}
				]
			})
		});
		return resp.ok;
	} catch (error) {
		return false;
	}
};

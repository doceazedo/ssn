import { Payment } from 'mercadopago';
import { addDonation } from 'warehouse';
import { env } from '$env/dynamic/private';
import { error } from '@sveltejs/kit';
import { mercadoPago } from '$lib/api/mercado-pago';
import { giveRank } from '$lib/api/cmd';
import type { PaymentResponse } from 'mercadopago/dist/clients/payment/commonTypes';
import {
	sendDonationError,
	sendDonationLog,
	sendDonationMessage,
	sendMercadoPagoLog
} from '$lib/api/discord.js';

export const POST = async ({ request }) => {
	let body;
	try {
		body = await request.json();
	} catch (e) {
		throw error(400, 'Invalid body');
	}

	const signature = request.headers.get('x-signature');
	if (!signature) throw error(400, 'Signature header not found');
	const signatureV1 =
		signature
			.split(',')
			.map((x) => x.split('='))
			.find(([key]) => key == 'v1')?.[1] || null;
	if (!signatureV1) throw error(400, 'Signature V1 not found');
	if (signatureV1 !== env.MERCADOPAGO_WEBHOOK_SIGNATURE) throw error(401, 'Invalid signature');

	const type = body.type;
	if (!type || type !== 'payment') throw error(400, 'Webhook must be of type payment');

	const paymentId = body?.data?.id;
	if (!paymentId) throw error(400, 'Invalid payment ID');

	let paymentData: PaymentResponse;
	try {
		const payment = new Payment(mercadoPago);
		paymentData = await payment.get({
			id: paymentId
		});
	} catch (e) {
		throw error(500, 'Could not get payment');
	}

	await sendMercadoPagoLog(body);

	if (paymentData.status !== 'approved') throw error(422);

	const items = paymentData.additional_info?.items || [];
	const ranks = await Promise.all(
		items.map(async (item) => {
			// FIXME: debug, remove this later
			if (item.id === 'donate-30d') item.id = 'donate:DoceAzedo:30d';

			const data = item.id.split(':');
			if (data.length !== 3) return;
			const [product, player, days] = data;
			if (product !== 'donate') return;

			const rank = await giveRank(player, 'donator', days);
			// if (!rank) return false;

			const donation = await addDonation(
				{
					amount: item.unit_price * item.quantity,
					paymentId,
					gateway: 'MERCADO_PAGO',
					ownerName: player
				},
				parseInt(days)
			);
			if (donation) {
				await sendDonationLog(donation, days);
				await sendDonationMessage(donation);
			}
			return !!donation;
		})
	);
	const ok = ranks.every((x) => !!x);
	if (!ok) {
		await sendDonationError(body, paymentId);
		throw error(500, 'Could not process items');
	}

	return new Response(null, {
		status: 204
	});
};

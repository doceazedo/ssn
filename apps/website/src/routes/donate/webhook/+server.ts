import crypto from 'crypto';
import { Payment } from 'mercadopago';
import { addDonation, giveDonationBadge } from 'warehouse';
import { env } from '$env/dynamic/private';
import { error } from '@sveltejs/kit';
import { mercadoPago } from '$lib/api/mercado-pago';
import { broadcast, giveRank } from '$lib/api/cmd';
import type { PaymentResponse } from 'mercadopago/dist/clients/payment/commonTypes';
import {
	sendDonationError,
	sendDonationLog,
	sendDonationMessage,
	sendMercadoPagoLog
} from '$lib/api/discord.js';

export const POST = async ({ request, url }) => {
	let body;
	try {
		body = await request.json();
	} catch (e) {
		throw error(400, 'Invalid body');
	}

	const signature = request.headers.get('x-signature');
	if (!signature) throw error(400, 'Signature header not found');

	const requestId = request.headers.get('x-request-id');
	if (!requestId) throw error(400, 'Request ID not found');

	const signatureMap = new Map(signature.split(',').map((x) => x.split('=')) as [string, string][]);
	const signatureV1 = signatureMap.get('v1');
	const timestamp = signatureMap.get('ts');
	if (!signatureV1 || !timestamp) throw error(400, 'Invalid signature V1 not found');

	const dataId = url.searchParams.get('data.id');
	if (!dataId) throw error(400, 'Data ID not found');

	const signatureTemplateParsed = `id:${dataId};request-id:${requestId};ts:${timestamp};`;
	const cyphedSignature = crypto
		.createHmac('sha256', env.MERCADOPAGO_WEBHOOK_SIGNATURE)
		.update(signatureTemplateParsed)
		.digest('hex');
	if (cyphedSignature !== signatureV1) throw error(401, 'Invalid signature');

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
			const data = item.id.split(':');
			if (data.length !== 3) return;
			const [product, player, days] = data;
			if (product !== 'donate') return;

			const rank = await giveRank(player, 'donator', days);
			if (!rank) return false;

			const donation = await addDonation(
				{
					amount: item.unit_price * item.quantity,
					paymentId,
					gateway: 'MERCADO_PAGO',
					ownerName: player
				},
				parseInt(days)
			);
			await giveDonationBadge(player);
			if (donation) {
				await sendDonationLog(donation, days);
				await sendDonationMessage(donation);
				await broadcast(
					[
						' ',
						`§6§l${donation.ownerName} §b§ldoou §6§lR$ ${donation.amount} §b§lpara o SSN!`,
						`§b§lDoe em §6§l§nhttps://${env.PUBLIC_IDENTITY_URL}/doar§b§l e ganhe recompensas!`,
						' '
					],
					true
				);
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

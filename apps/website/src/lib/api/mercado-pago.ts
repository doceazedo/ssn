import { MercadoPagoConfig } from 'mercadopago';
import { env } from '$env/dynamic/private';

export const mercadoPago = new MercadoPagoConfig({
	accessToken: env.MERCADOPAGO_ACCESS_TOKEN || ''
});

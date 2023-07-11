import * as yup from 'yup';
import { getUserConnection, updateConnection } from 'warehouse';
import { loggedInOnly, validateRequest } from '$lib/middlewares';
import { error, json, type RequestHandler } from '@sveltejs/kit';
import type { Service } from '@prisma/client';

export type PublicSocialsParams = {
	service: Service;
	isPublic: boolean;
};

export const PATCH: RequestHandler = async ({ request, locals, cookies }) =>
	validateRequest<PublicSocialsParams>(
		request,
		yup.object().shape({
			service: yup.string(),
			isPublic: yup.boolean()
		}),
		async (body) =>
			loggedInOnly(locals, cookies, async (identity) => {
				const connection = await getUserConnection(identity.uuid, body.service);
				if (!connection) throw error(400, 'User is not connected to this service');

				const updatedConnection = await updateConnection(connection.id, {
					isPublic: body.isPublic
				});
				if (!updatedConnection) throw error(500, 'Could not update connection');

				return json({
					connection: {
						service: updatedConnection.service,
						isPublic: updatedConnection.isPublic
					}
				});
			})
	);

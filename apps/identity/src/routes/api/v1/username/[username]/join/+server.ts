import { prisma } from 'warehouse';
import { error, json } from '@sveltejs/kit';
import { getUsername, updateUsername } from 'warehouse';
import type { RequestHandler } from '@sveltejs/kit';
import { tokenOnly } from '$lib/middlewares';

export const POST: RequestHandler = async ({ params, request }) =>
	tokenOnly(request, async () => {
		if (!params.username) throw error(400);

		const username = await getUsername(params.username);
		if (!username) throw error(404);

		const updatedUsername = await updateUsername(username.name, {
			firstJoin: !username.lastSeen ? new Date() : undefined,
			joinCount: ++username.joinCount,
			lastSeen: new Date(),
			isOnline: true
		});

		await giveBirthdayBadge(username.name);

		return json({
			username: updatedUsername
		});
	});

const giveBirthdayBadge = async (username: string) => {
	if (new Date() > new Date('Mar 25 2024 00:00:00 GMT-0300')) return;

	const slug = '4th-anniversary';
	const label = 'Feliz 4 anos, SSN!';
	const badge = await prisma.badge.upsert({
		where: {
			slug
		},
		create: {
			slug,
			label
		},
		update: {}
	});
	if (!badge) return;

	await prisma.usernameBadges.create({
		data: {
			ownerName: username,
			badgeSlug: slug
		}
	});
};

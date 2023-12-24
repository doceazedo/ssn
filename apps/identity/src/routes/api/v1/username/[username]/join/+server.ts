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

		await giveSeasonBadges(username.name);

		return json({
			username: updatedUsername
		});
	});

const giveSeasonBadges = async (username: string) => {
	let slug = 'christmas-2023';
	let label = 'Feliz Natal de 2023!';

	const isPastChristmas = new Date() > new Date('Dec 27 2023 00:00:00 GMT-0300');
	const isBeforeNewYear = new Date() < new Date('Dec 31 2023 00:00:00 GMT-0300');
	const isPastHolidays = new Date() > new Date('Jan 03 2024 00:00:00 GMT-0300');

	if (isPastChristmas) {
		slug = 'new-year-2024';
		label = 'Feliz 2024!';
	}
	if (isPastChristmas && isBeforeNewYear) return;
	if (isPastHolidays) return;

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

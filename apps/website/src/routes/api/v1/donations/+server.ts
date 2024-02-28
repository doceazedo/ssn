import { json } from '@sveltejs/kit';
import { getUserDonations } from 'warehouse';

export const GET = async () => {
	const donations = await getUserDonations('DoceAzedo');
	return json(donations);
};

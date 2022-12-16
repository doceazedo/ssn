import { redirect } from "@sveltejs/kit";
import type { LayoutServerLoad } from './$types';

export const load: LayoutServerLoad = ({ locals }) => {
  const { identity } = locals;
  if (identity.role !== 'ADMIN') throw redirect(307, '/dashboard');
}

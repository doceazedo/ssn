import type { LayoutServerLoad } from './$types';

export const load: LayoutServerLoad = ({ locals }) => {
  const { identity } = locals;
  return {
    identity
  };
}

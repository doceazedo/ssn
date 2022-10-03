import { json } from "@sveltejs/kit";
import { createFlow, getFlowByCode } from "$lib/controllers/flow";
import type { RequestHandler } from '@sveltejs/kit';

export const GET: RequestHandler = async () => {
  const createdFlow = await createFlow({
    username: 'DoceAzedo',
    ip: '187.84.244.135'
  });
  const validFlow = await getFlowByCode('ace37');
  const invalidFlow = await getFlowByCode('xyz789');

  return json({
    createdFlow,
    validFlow,
    invalidFlow
  });
};

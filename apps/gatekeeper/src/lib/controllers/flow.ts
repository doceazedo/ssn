import { connectDatabase, redis } from ".";
import { generateCode } from "$lib/utils";

type FlowData = {
  username: string;
  ip: string;
  grantKey?: string;
};

type Flow = {
  code: string;
} & FlowData;

const flowDurationSec = 45;

export const getFlowByCode = async (code: string): Promise<Flow | null> => {
  await connectDatabase();
  const flow = await redis.get(`flows:${code}`);
  if (!flow) return null;

  const flowData = JSON.parse(flow);
  return { code, ...flowData };
}

export const setFlow = async (code: string, data: FlowData): Promise<Flow | null> => {
  await connectDatabase();
  const result = await redis.setEx(`flows:${code}`, flowDurationSec, JSON.stringify(data));
  if (result !== 'OK') return null;
  return { code, ...data };
}

export const createFlow = async (data: FlowData): Promise<Flow | null> => {
  await connectDatabase();
  const code = generateCode();
  return await setFlow(code, data);
}

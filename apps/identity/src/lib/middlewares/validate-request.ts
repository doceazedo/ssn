import type { ObjectSchema } from "yup";
import { error } from "@sveltejs/kit";

export const validateRequest = async <T>(request: Request, schema: ObjectSchema<any>, callback: (body: T) => any) => {
  let body;
  try {
    body = await request.json();
  } catch (e) {
    throw error(400, 'Requisição inválida');
  }

  let validRequest: T;
  try {
    await schema.validate(body);
    validRequest = schema.cast(body) as T;
  } catch (e: any) {
    const errorMessage = e?.errors?.[0];
    if (errorMessage) throw error(400, errorMessage);
    throw error(400, 'Requisição inválida');
  }

  return callback(validRequest);
}
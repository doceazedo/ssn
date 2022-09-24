export const validateUsername = (username: string) =>
  /^[a-zA-Z0-9_]{2,16}$/mg.test(username);
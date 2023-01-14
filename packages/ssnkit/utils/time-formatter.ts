export const formatDuration = (durationInSeconds: number): string => {
  let builder = '';
  const days = Math.floor(durationInSeconds / 86400);
  const hours = Math.floor((durationInSeconds % 86400) / 3600);
  const minutes = Math.floor((durationInSeconds % 3600) / 60);
  const seconds = Math.floor(durationInSeconds % 60);

  if (days > 0) {
    builder += `${days} dia`;
    if (days > 1) builder += 's';
  }
  
  if (hours > 0) {
    if (builder !== '') builder += ', ';
    builder += `${hours} hora`;
    if (hours > 1) builder += 's';
  }

  if (minutes > 0) {
    if (builder !== '') builder += ', ';
    builder += `${minutes} minuto`;
    if (minutes > 1) builder += 's';
  }

  if (seconds > 0) {
    if (builder !== '') builder += ' e ';
    builder += `${seconds} segundo`;
    if (seconds > 1) builder += 's';
  }

  return builder;
}

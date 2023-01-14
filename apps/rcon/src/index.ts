import { Server, Socket } from "socket.io";
import * as dotenv from 'dotenv';
import { registerConsoleHandlers } from './handlers/console';
import { registerAuthMiddlewares } from './middlewares/auth';

dotenv.config();

const io = new Server({
  path: '/',
  cors: {
    origin: process.env.PUBLIC_IDENTITY_URL,
    credentials: true
  }
});
const port = 443;

const onConnection = (socket: Socket) => {
  registerConsoleHandlers(io, socket);
};

registerAuthMiddlewares(io);

io.on("connection", onConnection);
io.listen(port);

console.log(`Starting on port ${port}`);

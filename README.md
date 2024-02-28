<p align="center">
  <img src="./packages/ssnkit/assets/img/ssn-icon.png" width="100" />
</p>

<p align="center">
  Over-engineered open-source Minecraft anarchy server — made in Brasil 🇧🇷✨
</p>

## 🗂 What's inside?

This monorepo uses [npm](https://www.npmjs.com/) as a package manager. It includes the following applications:

- [`apps`](./apps): web applications related to the server
- [`packages`](./packages): libraries shared between apps
- [`plugins`](./plugins): first-party Minecraft plugins source code
- [`servers`](./servers): the Minecraft servers configuration files and plugin binaries

## 📦 Dependencies

Make sure you have **Node.js v20** installed with **npm v8** or up. You can easily change your Node version using [nvm](https://github.com/nvm-sh/nvm):

```bash
nvm install 20
nvm use 20
```

For plugin development, you will need **JDK 17** and **Maven**. To install them on macOS using [Homebrew](https://brew.sh), run:

```bash
brew install openjdk@17 maven
```

Finally, you will also need to have **Docker** and **Docker Compose** installed. You can easily get both by installing the much recommended [Docker Desktop](https://www.docker.com/products/docker-desktop).

## ⚡️ Get started

First off, install the dependencies by running the following command:

```bash
npm install
```

Then, you will need to setup your environment variables. You can do this by copying the example file:

```bash
cp .env.example .env
```

Now, open your `/etc/hosts` file and add these lines to the end:

```
127.0.0.1 ssn.local id.ssn.local gk.ssn.local rcon.ssn.local cmd.ssn.local
::1       ssn.local id.ssn.local gk.ssn.local rcon.ssn.local cmd.ssn.local
```

You can now start all the containers by running this command:

```bash
npm run start
```

🎉 You should now be able to join the Minecraft server using the IP **ssn.local** and open [http://ssn.local](http://ssn.local) in your browser.

## 🧰 Build

To build a plugin and copy the artifact to the server, you can run this command:

```bash
# npm run build:plugin-name
npm run build:catraca
```

All services are rebuilt on start by default.

## 🚀 Deploy

For deploying in production you can generally follow the development environment steps and:

- Only expose the `25565` and `80` ports from your server
- Setup the environment variables inside ".env" properly
  - Generate random and strong passwords for the keys
  - Configure the memory allocation according to your server
  - Fill in external sevice keys (like Discord, Turnstile and SendGrid)
  - Update the volume mount points to local paths

For now™, you'll need to build the containers and run them on your server like so:

```bash
chmod +x ./scripts/deploy.sh
./scripts/deploy.sh
```

## 🤒 Known issues

These are known issues with the current setup of this project:

- As you restart your instance multiple times, a gigantic build cache will form until your containers are out of space. When that happens, run `docker system prune -af --volumes` to delete all your docker containers and volumes.
- Docker image building and publishing are not currently configured. Therefore, deployment requires manual steps and can take up to 10 minutes to complete.

## 🤝 Contributing

First of all, thank you for your interest in making SSN better! Contributions are always welcomed. Feel free to [open an issue](https://github.com/doceazedo/ssn/issues) with your suggestion or bug report, preferably in Brazilian Portuguese. Please, read our [contribution guidelines](CONTRIBUTING.md) before working on an issue.

## 📝 License

The SSN project is licensed under the [GPLv3 License](LICENSE).

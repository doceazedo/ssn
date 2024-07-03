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

```sh
$ nvm install 20
$ nvm use 20
```

For plugin development, you will need **JDK 17** and **Maven**. To install them on macOS using [Homebrew](https://brew.sh), run:

```sh
$ brew install openjdk@17 maven
```

Finally, you will also need to have **Docker** and **Docker Compose** installed. You can easily get both by installing the much recommended [Docker Desktop](https://www.docker.com/products/docker-desktop).

## ⚡️ Get started

First off, install the dependencies by running the following command:

```sh
$ npm install
```

Then, you will need to setup your environment variables. You can do this by copying the example file:

```sh
$ cp .env.example .env
```

Now, open your `/etc/hosts` file and add these lines to the end:

```
127.0.0.1 ssn.local id.ssn.local gk.ssn.local rcon.ssn.local cmd.ssn.local
::1       ssn.local id.ssn.local gk.ssn.local rcon.ssn.local cmd.ssn.local
```

You can now start all the containers by running this command:

```sh
$ npm run start
```

🎉 You should now be able to join the Minecraft server using the IP **ssn.local** and open [http://ssn.local](http://ssn.local) in your browser.

## 🧰 Build

To build a plugin and copy the artifact to the server, you can run this command:

```sh
# npm run build:plugin-name
$ npm run build:catraca
```

To build (and publish) the server Docker images, run:

```sh
$ chmod +x ./scripts/build-and-publish.sh # once
$ ./scripts/build-and-publish.sh
```

## 🚀 Deploy

For deploying in production, clone this repository to your server. Alternatively, copy only the following files to your server:

- [docker-compose.prod.yml](./docker-compose.prod.yml)
- [.env.example](./.env.example) (rename this to .env)
- [/servers](./servers)

Setup the environment variables inside ".env" properly:

- Generate random and strong passwords for the keys
- Configure the memory allocation according to your server
- Fill in external sevice keys (like Discord, Turnstile and SendGrid)
- Update the volume mount points to local paths

You will need to expose the `25565` and `80` ports from your server according to your hosting provider instructions.

Then simply run:

```sh
$ chmod +x ./scripts/start.sh # once
$ ./scripts/start.sh
```

### 🔄 Auto restart

To enable automatic restarts, you'll need to setup a cron job to run the [`scripts/restart.sh`](./scripts/restart.sh) script. Begin by making all scripts in the scripts directory executable:

```sh
$ find scripts -type f -iname "*.sh" -exec chmod +x {} \;
```

Then, get the full path to the restart script:

```sh
$ realpath ./scripts/restart.sh
# /path/to/restart.sh
```

Finally, you can use `crontab` to add your cron job as you wish. I like to run it every day, a minute before 6am (to account for the restart warning) — remember to check your system time beforehand!

```sh
$ crontab -e
# if vim opens by default you can also use:
# EDITOR="nano" crontab -e
```

```
59 5 * * * /path/to/restart.sh
```

If you need help with cron, you can refer to [crontab.guru](https://crontab.guru).

## 🤝 Contributing

First of all, thank you for your interest in making SSN better! Contributions are always welcomed. Feel free to [open an issue](https://github.com/doceazedo/ssn/issues) with your suggestion or bug report, preferably in Brazilian Portuguese. Please, read our [contribution guidelines](CONTRIBUTING.md) before working on an issue.

## 📝 License

The SSN project is licensed under the [GPLv3 License](LICENSE).

<h1 align="center">
  SSN.gg 🍉
</h1>

<p align="center">
    This is the official repository for every part that composes the open-source Minecraft anarchy server ✨
</p>

> **Warning**: this project is under heavy development, do not expect anything to work at all at this point.

## 🗂 What's inside?

This monorepo uses [npm](https://www.npmjs.com/) as a package manager. It includes the following applications:

- [`apps`](./apps): all the services related to the server
- [`libraries`](./libraries): packages shared between services
- [`plugins`](./plugins): all first-party Spigot plugins
- [`servers`](./servers): the Paper servers configuration files and plugins

## 🧰 Build

First off, install the dependencies by running the following command:

```
npm install
```

Then to build all apps and packages, run:

```
npm run build
```

## 🚧 Develop

To start the development environment, run the following command:

```
npm run dev
```

## 🚀 Deploy

To start the production environment, run the following command:

```
npm run start
```

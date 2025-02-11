/// <reference path="../pb_data/types.d.ts" />

onRecordAuthWithPasswordRequest((e) => {
  if (!e.record) {
    e.next();
    return;
  }

  const legacyPassword = e.record.getString("legacyPassword");
  if (!legacyPassword) {
    e.next();
    return;
  }

  const Sha256 = require(`${__hooks}/sha256.js`);
  const hash = legacyPassword;
  const salt = hash.slice(64);
  const originalPassHash = hash.slice(0, 64);
  const currentPassHash = Sha256.hash(salt + e.password).toString();

  if (originalPassHash === currentPassHash) {
    e.record.set("legacyPassword", "");
    e.record.setPassword(e.password);
    e.app.save(e.record);
  }

  e.next();
}, "users");

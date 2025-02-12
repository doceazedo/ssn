/// <reference path="../pb_data/types.d.ts" />
migrate((app) => {
  const collection = app.findCollectionByNameOrId("pbc_2981925642")

  // update collection data
  unmarshal({
    "indexes": [
      "CREATE UNIQUE INDEX `idx_51V7xYIzMa` ON `usernames` (LOWER(`name`))"
    ]
  }, collection)

  return app.save(collection)
}, (app) => {
  const collection = app.findCollectionByNameOrId("pbc_2981925642")

  // update collection data
  unmarshal({
    "indexes": []
  }, collection)

  return app.save(collection)
})

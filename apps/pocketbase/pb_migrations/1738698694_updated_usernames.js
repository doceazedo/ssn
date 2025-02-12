/// <reference path="../pb_data/types.d.ts" />
migrate((app) => {
  const collection = app.findCollectionByNameOrId("pbc_2981925642")

  // update collection data
  unmarshal({
    "createRule": "@request.auth.id != \"\" &&\nowner = @request.auth.id"
  }, collection)

  return app.save(collection)
}, (app) => {
  const collection = app.findCollectionByNameOrId("pbc_2981925642")

  // update collection data
  unmarshal({
    "createRule": null
  }, collection)

  return app.save(collection)
})

/// <reference path="../pb_data/types.d.ts" />
migrate((app) => {
  const collection = app.findCollectionByNameOrId("pbc_2981925642")

  // update collection data
  unmarshal({
    "listRule": "(@request.query.page = \"1\" && @request.query.perPage = \"1\") ||\n@request.auth.id = owner.id"
  }, collection)

  return app.save(collection)
}, (app) => {
  const collection = app.findCollectionByNameOrId("pbc_2981925642")

  // update collection data
  unmarshal({
    "listRule": "@request.query.page = \"1\" && @request.query.perPage = \"1\""
  }, collection)

  return app.save(collection)
})

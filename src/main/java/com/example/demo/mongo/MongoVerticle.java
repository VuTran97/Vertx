package com.example.demo.vertx.mongo;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;

public class MongoVerticle extends AbstractVerticle {

  /**
   * create mongo client and insert user "vuth" with password "123456" to collection users
   * @throws Exception
   */
  @Override
  public void start() throws Exception {
    JsonObject config = Vertx.currentContext().config();
    String uri = config.getString("mongo_uri");
    if (uri == null) {
      uri = "mongodb://localhost:27017";
    }
    String db = config.getString("mongo_db");
    if (db == null) {
      db = "vertx";
    }
    JsonObject mongoconfig = new JsonObject().put("connection_string", uri).put("db_name", db);
    MongoClient mongoClient = MongoClient.createShared(vertx, mongoconfig);
    JsonObject user = new JsonObject().put("username", "vuth").put("password", "123456");
    mongoClient.save("users", user,  id -> {
      System.out.println("Inserted id: " + id.result());
      mongoClient.find("users", new JsonObject().put("username", "vuth"),  res -> {
        System.out.println("username is " + res.result().get(0).getString("username"));
      });
    });
  }

  public static void main(String[] args) {
    Vertx vertx1 = Vertx.vertx();
    vertx1.deployVerticle(new MongoVerticle());
  }
}

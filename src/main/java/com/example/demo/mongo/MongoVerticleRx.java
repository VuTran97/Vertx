package com.example.demo.mongo;

import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.core.AbstractVerticle;
import io.vertx.reactivex.core.Vertx;
import io.vertx.reactivex.ext.mongo.MongoClient;

public class MongoVerticleRx extends AbstractVerticle {

    @Override
    public void start() throws Exception {
        insertAndFind();
    }

    /**
     * create mongo client
     * @return
     */
    private MongoClient createMongoClient(){
        JsonObject mongoconfig = new JsonObject().put("connection_string", "mongodb://localhost:27017").put("db_name", "vertx");
        return MongoClient.createShared(vertx, mongoconfig);

    }

    /**
     * create and find user vuth
     */
    private void insertAndFind(){
        MongoClient mongoClient = createMongoClient();
        JsonObject user = new JsonObject().put("username", "vuth").put("password", "123456");
        mongoClient.rxSave("users", user)
                .doOnSuccess(id -> {
                            System.out.println("Inserted document: " + id);
                        }).flatMapSingle(id -> {
                    return mongoClient.rxFind("users", new JsonObject().put("username", "vuth"));
                }).subscribe(result -> {
                    System.out.println("Results " + result);
                }, error -> {
                    System.out.println("Err");
                    error.printStackTrace();
                });
    }

    public static void main(String[] args) {
        Vertx vertx1 = Vertx.vertx();
        vertx1.deployVerticle(new MongoVerticleRx());
    }


}

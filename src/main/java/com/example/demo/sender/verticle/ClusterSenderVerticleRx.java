package com.example.demo.sender.verticle;


import io.reactivex.Observable;
import io.vertx.core.Vertx;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.reactivex.core.AbstractVerticle;
import io.vertx.reactivex.core.eventbus.EventBus;
import io.vertx.reactivex.ext.web.Router;
import io.vertx.reactivex.ext.web.RoutingContext;

public class ClusterSenderVerticleRx extends AbstractVerticle {

    private final Logger logger = LoggerFactory.getLogger(ClusterSenderVerticleRx.class);

    private static final String ADDRESS = "sender.vertx.addr";

    /**
     * create api /sendForAll to send message for receiver -> convert use rxjava
     * @throws Exception
     */
    @Override
    public void start() throws Exception {
        Router router = Router.router(vertx);
        router.post("/sendForAll/:" + "message").handler(this::sendMessageForAllReceivers);
        vertx.createHttpServer().requestHandler(router).rxListen(8000).subscribe(server -> {
            logger.info("Started on port {0}", String.valueOf(server.actualPort()));
        }, throwable -> {
            logger.error(throwable.getMessage());
        });
    }
    /**
     * public event bus send message for all members -> apply rx
     * @param routingContext
     */
    private void sendMessageForAllReceivers(RoutingContext routingContext) {
        final String message = routingContext.request().getParam("message");
        final EventBus eventBus = vertx.eventBus();
        eventBus.publish(ADDRESS, message);
        logger.info("Current Thread Id {0} Is Clustered {1} ",
                Thread.currentThread().getId(), vertx.isClustered());
        routingContext.response().end(message);
    }



}

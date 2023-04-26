package com.example.demo.sender.verticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

public class ClusterSenderVerticle extends AbstractVerticle {
  private final Logger logger = LoggerFactory.getLogger(ClusterSenderVerticle.class);

  private static final String ADDRESS = "sender.vertx.addr";

  /**
   * create api /sendForAll to send message for receiver
   * @throws Exception
   */
  @Override
  public void start() throws Exception {
    Router router = Router.router(vertx);
    router.post("/sendForAll/:" + "message").handler(this::sendMessageForAllReceivers);
    vertx.createHttpServer().requestHandler(router).listen(8000);

  }

  /**
   * public event bus send message for all members
   * @param routingContext
   */
  private void sendMessageForAllReceivers(RoutingContext routingContext){
    final EventBus eventBus = vertx.eventBus();
    final String message = routingContext.request().getParam("message");
    eventBus.publish(ADDRESS, message);
    logger.info("Current Thread Id {0} Is Clustered {1} ",
        Thread.currentThread().getId(), vertx.isClustered());
    routingContext.response().end(message);
  }

}

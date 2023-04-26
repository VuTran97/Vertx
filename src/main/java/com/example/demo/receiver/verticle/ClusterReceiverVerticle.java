package com.example.demo.receiver.verticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

public class ClusterReceiverVerticle extends AbstractVerticle {

  private static final Logger log = LoggerFactory.getLogger(ClusterReceiverVerticle.class);
  private static final String ADDRESS = "sender.vertx.addr";

  /**
   * receive message from sender
   * @throws Exception
   */
  @Override
  public void start() throws Exception {
    final EventBus eventBus = vertx.eventBus();
    eventBus.consumer(ADDRESS, receivedMessage -> {
      log.info("Received message: {0} ", receivedMessage.body());
    });

    log.info("Receiver ready!");
  }


}

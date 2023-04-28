package com.example.demo.receiver.verticle;

import io.reactivex.Observable;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.reactivex.core.AbstractVerticle;
import io.vertx.reactivex.core.eventbus.EventBus;


public class ClusterReceiverVerticleRx extends AbstractVerticle {

    private final Logger log = LoggerFactory.getLogger(ClusterReceiverVerticleRx.class);

    private static final String ADDRESS = "sender.vertx.addr";

    /**
     * receive message from sender -> apply rx
     * @throws Exception
     */
    @Override
    public void start() throws Exception {
         final EventBus eventBus = vertx.eventBus();
        Observable<String> msg = eventBus.<String>consumer(ADDRESS).bodyStream().toObservable();
        msg.subscribe(s -> {
            log.info("Received message: {0} ", s);
        });
        log.info("Receiver ready!");
    }

}

package com.example.demo.receiver.main;

import com.example.demo.helper.ClusterConfiguratorHelper;
import com.example.demo.receiver.verticle.ClusterReceiverVerticleRx;
import io.vertx.core.VertxOptions;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.core.spi.cluster.ClusterManager;
import io.vertx.reactivex.core.Vertx;
import io.vertx.spi.cluster.hazelcast.HazelcastClusterManager;

public class ClusterReceiverStarerRx {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClusterReceiverStarerRx.class);

    /**
     * deploy cluster for all receiver members -> apply rx
     * @param args
     */
    public static void main(String[] args) {
        final ClusterManager mgr = new HazelcastClusterManager(
                ClusterConfiguratorHelper.getHazelcastConfiguration());
        final VertxOptions options = new VertxOptions().setClusterManager(mgr);
        Vertx.rxClusteredVertx(options).subscribe(cluster -> {
            cluster.rxDeployVerticle(new ClusterReceiverVerticleRx())
                    .subscribe(res -> {
                        LOGGER.info("Deployment id is: {0}", res);
                    }, error -> {
                        LOGGER.error("Deployment failed: {0}", error.getMessage());
                    });
                });
    }
}

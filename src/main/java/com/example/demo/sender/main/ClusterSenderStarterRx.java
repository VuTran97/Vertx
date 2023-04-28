package com.example.demo.sender.main;

import com.example.demo.helper.ClusterConfiguratorHelper;
import com.example.demo.sender.verticle.ClusterSenderVerticleRx;
import io.vertx.core.VertxOptions;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.core.spi.cluster.ClusterManager;
import io.vertx.reactivex.core.Vertx;
import io.vertx.spi.cluster.hazelcast.HazelcastClusterManager;

public class ClusterSenderStarterRx {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClusterSenderStarterRx.class);

    /**
     * deploy cluster for Sender message -> apply rx
     * @param args
     */
    public static void main(String[] args) {
        final ClusterManager mgr = new HazelcastClusterManager(ClusterConfiguratorHelper.getHazelcastConfiguration());
        final VertxOptions options = new VertxOptions().setClusterManager(mgr);
        Vertx.rxClusteredVertx(options).subscribe(cluster -> {
            cluster.rxDeployVerticle(new ClusterSenderVerticleRx())
                    .subscribe(res -> {
                        LOGGER.info("Deployment id is: {0}", res);
                    }, error -> {
                        LOGGER.error("Deployment failed: {0}", error.getMessage());
                    });
                });
    }
}

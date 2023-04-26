package com.example.demo.receiver.main;

import com.example.demo.helper.ClusterConfiguratorHelper;
import com.example.demo.receiver.verticle.ClusterReceiverVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.core.spi.cluster.ClusterManager;
import io.vertx.spi.cluster.hazelcast.HazelcastClusterManager;

public class ClusterReceiverStarter {
  private static final Logger LOGGER = LoggerFactory.getLogger(ClusterReceiverStarter.class);

  /**
   * deploy cluster for all receiver members
   * @param args
   */
  public static void main(String[] args) {
    final ClusterManager mgr = new HazelcastClusterManager(
        ClusterConfiguratorHelper.getHazelcastConfiguration());
    final VertxOptions options = new VertxOptions().setClusterManager(mgr);
    Vertx.clusteredVertx(options, cluster -> {
      if (cluster.succeeded()) {
        cluster.result().deployVerticle(new ClusterReceiverVerticle(), res -> {
          if (res.succeeded()) {
            LOGGER.info("Deployment id is: {0}", res.result());
          } else {
            LOGGER.error("Deployment failed!");
          }
        });
      } else {
        LOGGER.error("Cluster up failed: ", cluster.cause());
      }
    });
  }
}

package com.example.demo.sender.main;

import com.example.demo.helper.ClusterConfiguratorHelper;
import com.example.demo.sender.verticle.ClusterSenderVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.core.spi.cluster.ClusterManager;
import io.vertx.spi.cluster.hazelcast.HazelcastClusterManager;

public class ClusterSenderStarter {

  private static final Logger LOGGER = LoggerFactory.getLogger(ClusterSenderStarter.class);

  /**
   * deploy cluster for Sender message
   * @param args
   */
  public static void main(String[] args){
    final ClusterManager mgr = new HazelcastClusterManager(ClusterConfiguratorHelper.getHazelcastConfiguration());
    final VertxOptions options = new VertxOptions().setClusterManager(mgr);
    Vertx.clusteredVertx(options, cluster -> {
      if (cluster.succeeded()) {
        cluster.result().deployVerticle(new ClusterSenderVerticle(), res -> {
          if(res.succeeded()){
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

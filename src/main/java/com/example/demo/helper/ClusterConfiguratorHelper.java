package com.example.demo.helper;

import com.hazelcast.config.Config;
import com.hazelcast.config.InterfacesConfig;
import com.hazelcast.config.JoinConfig;

public class ClusterConfiguratorHelper{

  private ClusterConfiguratorHelper(){}

  /**
   * config Hazel cluster
   * @return
   */
  public static Config getHazelcastConfiguration(){
    final Config config = new Config();
    final JoinConfig joinConfig = config.getNetworkConfig().getJoin();
    joinConfig.getMulticastConfig().setEnabled(false);
    joinConfig.getTcpIpConfig().setEnabled(true).addMember("127.0.0.1");

    final InterfacesConfig interfaceConfig = config.getNetworkConfig().getInterfaces();
    interfaceConfig.setEnabled(true).addInterface( "127.0.0.1" );

    return config;
  }
}

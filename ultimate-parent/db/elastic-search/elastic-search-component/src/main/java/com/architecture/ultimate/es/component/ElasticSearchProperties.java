package com.architecture.ultimate.es.component;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @author luyi
 */
@ConfigurationProperties("customize.es")
public class ElasticSearchProperties {
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 集群节点地址
     */
    private List<EsNode> nodes;
    /**
     * 默认的数据分片数
     */
    private int defaultShards = 3;
    /**
     * 默认的数据备份数
     */
    private int defaultReplicas = 1;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<EsNode> getNodes() {
        return nodes;
    }

    public void setNodes(List<EsNode> nodes) {
        this.nodes = nodes;
    }

    public int getDefaultShards() {
        return defaultShards;
    }

    public void setDefaultShards(int defaultShards) {
        this.defaultShards = defaultShards;
    }

    public int getDefaultReplicas() {
        return defaultReplicas;
    }

    public void setDefaultReplicas(int defaultReplicas) {
        this.defaultReplicas = defaultReplicas;
    }


}

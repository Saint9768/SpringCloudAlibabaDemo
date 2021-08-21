package com.saint.contentcenter.configuration;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.cloud.nacos.ribbon.NacosServer;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.alibaba.nacos.client.naming.core.Balancer;
import com.alibaba.nacos.client.naming.utils.Chooser;
import com.alibaba.nacos.client.naming.utils.Pair;
import com.alibaba.nacos.client.utils.LogUtils;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.BaseLoadBalancer;
import com.netflix.loadbalancer.Server;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 自定义支持Nacos权重的负载均衡策略
 * 可以通过继承AbstractLoadBalancerRule 或者 实现IRule来达成目的。
 *
 * @author 周鑫(玖枭)
 */
@Slf4j
public class NacosSameClusterWeightedRule extends AbstractLoadBalancerRule {

    @Autowired
    private NacosDiscoveryProperties nacosDiscoveryProperties;

    @Override
    public void initWithNiwsConfig(IClientConfig iClientConfig) {
        // 读取配置文件，并初始化NacosWeightedRule
    }

    @Override
    public Server choose(Object o) {
        try {
            // 1.拿到配置文件中配置的集群名称
            String clusterName = nacosDiscoveryProperties.getClusterName();
            // 获取目标version
            String targetV = nacosDiscoveryProperties.getMetadata().get("target-v");

            BaseLoadBalancer loadBalancer = (BaseLoadBalancer) this.getLoadBalancer();
            // 2.想要请求的微服务名称
            String name = loadBalancer.getName();

            // 3.实现服务相关的API
            NamingService namingService = nacosDiscoveryProperties.namingServiceInstance();

            // 1. 找到指定服务的所有实例 A
            List<Instance> instances = namingService.getAllInstances(name);
            // 1.1. 过滤掉不符个版本的实例
            if (!StringUtils.isEmpty(targetV)) {
                instances = instances.stream().filter(instance -> Objects.equals(instance.getMetadata().get("v"), targetV)).collect(Collectors.toList());
            }
            // 2. 过滤出相同集群下的所有实例 B
            List<Instance> sameClusterInstances = instances.stream().filter(instance -> Objects.equals(instance.getClusterName(), clusterName)).collect(Collectors.toList());

            // 3. 如果B为空，就用A
            List<Instance> instancesToBeChosen = new ArrayList<>();
            if (CollectionUtils.isEmpty(sameClusterInstances)) {
                instancesToBeChosen = instances;
                log.warn("发生跨集群的调用， name = {}, cluster-name = {}, instances = {}",
                        name, clusterName, instances);
            } else {
                instancesToBeChosen = sameClusterInstances;
            }
            // 4. 基于权重的负载均衡算法，返回一个实例
            Instance chosenInstance = ExtendBalance.getHostByRandomWeightExt(instancesToBeChosen);
            log.info("选中的实例为：port = {}, ip = {}", chosenInstance.getPort(), chosenInstance.getIp());
            return new NacosServer(chosenInstance);
        } catch (Exception e) {
            log.error("发生异常了！", e);
            return null;
        }
    }
}

class ExtendBalance extends Balancer {
    public static Instance getHostByRandomWeightExt(List<Instance> hosts) {
        return getHostByRandomWeight(hosts);
    }
}

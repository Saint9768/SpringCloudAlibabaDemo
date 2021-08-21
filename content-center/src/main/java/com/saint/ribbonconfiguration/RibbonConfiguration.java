package com.saint.ribbonconfiguration;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import com.saint.contentcenter.configuration.NacosSameClusterWeightedRule;
import com.saint.contentcenter.configuration.NacosWeightedRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Ribbon的配置类一定不能Spring扫描到。
 * Ribbon有自己的子上下文，Spring的父上下文如何和Ribbon的子上下文重叠，会有各种各样的问题。
 * 比如：Spring和SpringMVC父子上下文重叠会导致事务不生效；
 * @author 周鑫(玖枭)
 */
@Configuration
public class RibbonConfiguration {

    @Bean
    public IRule ribbonRule() {
        return new NacosSameClusterWeightedRule();
    }
}

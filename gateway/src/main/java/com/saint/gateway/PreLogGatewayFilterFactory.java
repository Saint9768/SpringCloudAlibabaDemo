package com.saint.gateway;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractNameValueGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

/**
 * @author Saint
 * @version 1.0
 * @createTime 2021-07-14 8:24
 */
@Slf4j
@Component
public class PreLogGatewayFilterFactory extends AbstractNameValueGatewayFilterFactory {
    @Override
    public GatewayFilter apply(NameValueConfig config) {
//        return ((exchange, chain) -> {
//            // 打日志务必要写在lambda里面
//            // config.getName()/getValue()获取到的是yaml文件中的第一个和第二个参数
//            log.info("请求进到过滤器了...{},{}", config.getName(), config.getValue());
//            ServerHttpRequest modifiedRequest = exchange.getRequest()
//                    .mutate()
//                    .build();
//            ServerWebExchange modifiedExchange = exchange.mutate()
//                    .request(modifiedRequest)
//                    .build();
//
//            return chain.filter(modifiedExchange);
//        });
        // 自定义路由Order
        GatewayFilter filter = ((exchange, chain) -> {
            // 打日志务必要写在lambda里面
            // config.getName()/getValue()获取到的是yaml文件中的第一个和第二个参数
            log.info("请求进到过滤器了...{},{}", config.getName(), config.getValue());
            ServerHttpRequest modifiedRequest = exchange.getRequest()
                    .mutate()
                    .build();
            ServerWebExchange modifiedExchange = exchange.mutate()
                    .request(modifiedRequest)
                    .build();

            return chain.filter(modifiedExchange);
        });
        return new OrderedGatewayFilter(filter, 10000);
    }
}

package com.saint.contentcenter.configuration;

import feign.Logger;
import org.springframework.context.annotation.Bean;

/**
 * Feign的配置类
 * 这个配置类不要加@Configuration注解了，否则必须挪到@ComponentScan注解扫描不到的地方
 * @author Saint
 */
public class UserCenterFeignConf {

    @Bean
    public Logger.Level level() {
        // 让Feign打印所有请求的细节
        return Logger.Level.FULL;
    }
}

package com.saint.contentcenter;

import org.springframework.web.client.RestTemplate;

/**
 * @author 周鑫(玖枭)
 */
public class SentinelTest {
    public static void main(String[] args) throws InterruptedException {
        RestTemplate restTemplate = new RestTemplate();
        for (int i = 0; i < 1000; i++) {
            String forObject = restTemplate.getForObject("http://localhost:28081/test", String.class);
            Thread.sleep(500);
        }
    }
}

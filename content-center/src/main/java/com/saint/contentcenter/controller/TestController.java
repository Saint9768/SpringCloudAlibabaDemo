package com.saint.contentcenter.controller;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.Tracer;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.context.ContextUtil;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.saint.contentcenter.dao.content.ShareMapper;
import com.saint.contentcenter.domain.dto.user.UserDTO;
import com.saint.contentcenter.domain.entity.content.Share;
import com.saint.contentcenter.feignclient.TestBaiduFeign;
import com.saint.contentcenter.feignclient.TestUserCenterFeignClient;
import com.saint.contentcenter.rocketmq.MySource;
import com.saint.contentcenter.sentineltest.TestControllerBlockHandler;
import com.saint.contentcenter.sentineltest.TestControllerFallbackHandler;
import com.saint.contentcenter.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Saint
 * @version 1.0
 * @createTime 2021-06-30 7:13
 */
@RestController
@Slf4j
@RefreshScope
public class TestController {

    @Autowired(required = false)
    private ShareMapper shareMapper;

    @Autowired
    private DiscoveryClient discoveryClient;

    @GetMapping("test")
    public List<Share> testInsert() {
        Share user = new Share();
        user.setTitle("xxx");
        user.setCover("xxx");
        user.setAuthor("??????");
        user.setBuyCount(1);
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        shareMapper.insertSelective(user);

        List<Share> shares = shareMapper.selectAll();
        return shares;
    }

    /**
     * ?????????????????????????????????????????????
     *
     * @return
     */
    @GetMapping("test2")
    public List<ServiceInstance> setDiscoveryClient() {
        List<ServiceInstance> instances = discoveryClient.getInstances("user-center");
        return instances;
    }

    @Autowired
    private TestUserCenterFeignClient testUserCenterFeignClient;

    @GetMapping("test3")
    public UserDTO query(UserDTO userDTO) {
        return testUserCenterFeignClient.query(userDTO);
    }

    @Autowired
    private TestBaiduFeign testBaiduFeign;

    @GetMapping("baidu")
    public String baiDuIndex() {
        return testBaiduFeign.idnex();
    }

    @Autowired
    private TestService testService;

    @GetMapping("test-a")
    public String testA() {
        this.testService.common();
        return "test-a";
    }

    @GetMapping("test-b")
    public String testB() {
        this.testService.common();
        return "test-b";
    }

    @GetMapping("test-hot")
    @SentinelResource("hot")
    public String testB(@RequestParam(required = false) String a, @RequestParam(required = false) String b) {
        return a + " " + b;
    }

    @GetMapping("test-add-flow-rule")
    public String testAddFlowRule() {
        initFlowQpsRule();
        return "Saint";
    }

    // ????????????????????????????????????????????????????????????????????????
    private void initFlowQpsRule() {
        List<FlowRule> rules = new ArrayList<>();
        FlowRule rule = new FlowRule("/shares/{id}");
        // set limit qps to 1
        rule.setCount(1);
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        rule.setLimitApp("default");
        rules.add(rule);
        FlowRuleManager.loadRules(rules);
    }

    @GetMapping("test-sentinel-api")
    public String testSentinelAPI(@RequestParam(required = false) String a) {
        String resourceName = "test-sentinel-api";
        ContextUtil.enter(resourceName, "test-wfw");
        // ????????????sentinel???????????????, ????????????test-sentinel-api
        Entry entry = null;
        try {
            entry = SphU.entry("test-sentinel-api");
            // ????????????????????????
            if (StringUtils.isBlank(a)) {
                throw new IllegalArgumentException("a????????????");
            }
            return a;
        }
        // ???????????????????????????????????????????????????????????????BlockException
        catch (BlockException e) {
            log.warn("????????????????????????", e);
            return "????????????????????????";
        } catch (IllegalArgumentException e2) {
            // ??????IllegalArgumentException?????????????????????????????????.....???
            Tracer.trace(e2);
            return "???????????????";
        } finally {
            // ??????entry
            if (entry != null) {
                entry.exit();
            }
            ContextUtil.exit();
        }

    }

    @GetMapping("test-sentinel-resource")
    @SentinelResource(value = "test-sentinel-resource",
            blockHandler = "blockTest2",
            blockHandlerClass = TestControllerBlockHandler.class,
            fallback = "fallbackTest2",
            fallbackClass = TestControllerFallbackHandler.class)
    public String testSentinelResource(@RequestParam(required = false) String a) {
        // ????????????????????????
        if (StringUtils.isBlank(a)) {
            throw new IllegalArgumentException("a????????????");
        }
        return a;
    }

    /**
     * ??????????????????
     */
    public String blockTest(String a, BlockException e) {
        log.warn("???????????????????????? block", e);
        return "???????????????????????? block";
    }

    /**
     * sentinel 1.5????????????
     * sentinel 1.6??????????????????Throwable??????????????????
     */
    public String fallbackTest(String a, Throwable e) {
        log.warn("???????????????????????? fallback", e);
        return "???????????????????????? fallback";
    }

    @Autowired
    private Source source;

    @GetMapping("/test-stream")
    public String testStream() {
        source.output().send(MessageBuilder.withPayload("?????????").build());
        return "success";
    }
//
//    @Autowired
//    private MySource mySource;
//
//    @GetMapping("/test-stream2")
//    public String testStream2() {
//        mySource.output().send(MessageBuilder.withPayload("mySource-?????????").build());
//        return "success";
//    }

    @Value("${my.configuration}")
    private String myConfiguration;

    @GetMapping("/test-config")
    public String testConfiguration() {
        return this.myConfiguration;
    }

}

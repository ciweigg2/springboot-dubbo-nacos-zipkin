package com.mxc.web.paramflow;

import com.alibaba.csp.sentinel.datasource.ReadableDataSource;
import com.alibaba.csp.sentinel.datasource.nacos.NacosDataSource;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRuleManager;
import com.alibaba.csp.sentinel.slots.system.SystemRule;
import com.alibaba.csp.sentinel.slots.system.SystemRuleManager;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @author 马秀成
 * @date 2019/1/18
 * @jdk.version 1.8
 * @desc
 */
@Component
@Slf4j
public class PullRules {

    final String remoteAddress = "118.184.218.184:8848";
    final String groupId = "Sentinel:Demo";

    /**
     * 初始化规则配置(默认所有服务会使用TestResource流量配置)
     * 控制流量到每个服务 需要在接口添加 @SentinelResource(value = "TestResource", blockHandler = "handleException", blockHandlerClass = {ExceptionUtil.class})
     */
    @PostConstruct
    public void loadRules() {
        loadFlowRule();
        loadDegradeRule();
        loadSystemRule();
        loadParamFlowRule();
    }

	/**
     * 初始化流量规则
     */
    public void loadFlowRule(){
        final String dataId = "com.alibaba.csp.sentinel.demo.flow.rule";
        ReadableDataSource<String, List<FlowRule>> flowRuleDataSource = new NacosDataSource<>(remoteAddress, groupId, dataId,
                source -> JSON.parseObject(source, new TypeReference<List<FlowRule>>() {}));
        FlowRuleManager.register2Property(flowRuleDataSource.getProperty());
        log.info("初始化流量规则成功");
    }

    /**
     * 初始化降级规则
     */
    public void loadDegradeRule(){
        final String dataId = "com.alibaba.csp.sentinel.demo.degrade.rule";
        ReadableDataSource<String, List<DegradeRule>> degradeRuleDataSource = new NacosDataSource<>(remoteAddress, groupId, dataId,
                source -> JSON.parseObject(source, new TypeReference<List<DegradeRule>>() {}));
        DegradeRuleManager.register2Property(degradeRuleDataSource.getProperty());
        log.info("初始化降级规则成功");
    }

    /**
     * 初始化系统规则
     */
    public void loadSystemRule(){
        final String dataId = "com.alibaba.csp.sentinel.demo.system.rule";
        ReadableDataSource<String, List<SystemRule>> systemRuleDataSource = new NacosDataSource<>(remoteAddress, groupId, dataId,
                source -> JSON.parseObject(source, new TypeReference<List<SystemRule>>() {}));
        SystemRuleManager.register2Property(systemRuleDataSource.getProperty());
        log.info("初始化系统规则成功");
    }

    /**
     * 初始化热点规则
     */
    public void loadParamFlowRule(){
        final String dataId = "com.alibaba.csp.sentinel.demo.param.rule";
        ReadableDataSource<String, List<ParamFlowRule>> paramFlowRuleDataSource = new NacosDataSource<>(remoteAddress, groupId, dataId,
                source -> JSON.parseObject(source, new TypeReference<List<ParamFlowRule>>() {}));
        ParamFlowRuleManager.register2Property(paramFlowRuleDataSource.getProperty());
        log.info("初始化热点规则成功");
    }

}

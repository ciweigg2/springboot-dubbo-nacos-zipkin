package com.mxc.service.provider1.sentinel;

import com.alibaba.csp.sentinel.datasource.ReadableDataSource;
import com.alibaba.csp.sentinel.datasource.nacos.NacosDataSource;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
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
public class push {

    /**
     * 添加流量控制配置参数
     */
//    public static void main(String[] args) throws Exception {
//        final String remoteAddress = "118.184.218.184:8848";
//        final String groupId = "Sentinel:Demo";
//        final String dataId = "com.alibaba.csp.sentinel.demo.flow.rule";
//        final String rule = "[\n"
//                + "  {\n"
//                + "    \"resource\": \"TestResource\",\n"
//                + "    \"controlBehavior\": 0,\n"
//                + "    \"count\": 5.0,\n"
//                + "    \"grade\": 1,\n"
//                + "    \"limitApp\": \"default\",\n"
//                + "    \"strategy\": 0\n"
//                + "  }\n"
//                + "]";
//        ConfigService configService = NacosFactory.createConfigService(remoteAddress);
//        System.out.println(configService.publishConfig(dataId, groupId, rule));
//    }

    /**
     * 初始化流量配置(默认所有服务会使用TestResource流量配置)
     * 控制流量到每个服务 需要在接口添加 @SentinelResource(value = "TestResource", blockHandler = "handleException", blockHandlerClass = {ExceptionUtil.class})
     */
    @PostConstruct
    public void loadRules() {
        final String remoteAddress = "118.184.218.184:8848";
        final String groupId = "Sentinel:Demo";
        final String dataId = "com.alibaba.csp.sentinel.demo.flow.rule";

        ReadableDataSource<String, List<FlowRule>> flowRuleDataSource = new NacosDataSource<>(remoteAddress, groupId, dataId,
                source -> JSON.parseObject(source, new TypeReference<List<FlowRule>>() {}));
        FlowRuleManager.register2Property(flowRuleDataSource.getProperty());
        System.out.println("初始化规则成功");
    }

}

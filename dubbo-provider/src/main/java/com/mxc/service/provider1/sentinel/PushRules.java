package com.mxc.service.provider1.sentinel;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by Administrator on 2019/1/18.
 */
@Slf4j
@Component
public class PushRules {

	/**
	 * 配置nacos配置 以后可以项目初始化去配置的
	 *
	 * @throws Exception
	 */
	@PostConstruct
	public void init() throws Exception {
		setFlowRule();
		setDegradeRule();
		setSystemRule();
		setParamFlowRule();
	}

	/**
	 * 添加流量控制配置参数
	 */
	public static void setFlowRule() throws Exception {
		final String remoteAddress = "118.184.218.184:8848";
		final String groupId = "Sentinel:Demo";
		final String dataId = "com.alibaba.csp.sentinel.demo.flow.rule";
		final String rule = "[\n" +
				"  {\n" +
				"    \"resource\": \"FlowRuleA\",\n" +
				"    \"controlBehavior\": 0,\n" +
				"    \"count\": 5.0,\n" +
				"    \"grade\": 1,\n" +
				"    \"limitApp\": \"default\",\n" +
				"    \"strategy\": 0\n" +
				"  },\n" +
				"  {\n" +
				"    \"resource\": \"FlowRuleB\",\n" +
				"    \"controlBehavior\": 0,\n" +
				"    \"count\": 5.0,\n" +
				"    \"grade\": 1,\n" +
				"    \"limitApp\": \"default\",\n" +
				"    \"strategy\": 0\n" +
				"  }\n" +
				"]";
		ConfigService configService = NacosFactory.createConfigService(remoteAddress);
		log.info(String.valueOf(configService.publishConfig(dataId, groupId, rule)));
	}

	/**
	 * 添加流量控制配置参数 timeWindow 10秒后恢复正常访问的
	 */
	public static void setDegradeRule() throws Exception {
		final String remoteAddress = "118.184.218.184:8848";
		final String groupId = "Sentinel:Demo";
		final String dataId = "com.alibaba.csp.sentinel.demo.degrade.rule";
		final String rule = "[\n" +
				"  {\n" +
				"    \"resource\": \"DegradeRuleA\",\n" +
				"    \"count\": 5.0,\n" +
				"    \"grade\": 0,\n" +
				"    \"passCount\": 0,\n" +
				"    \"timeWindow\": 10\n" +
				"  },\n" +
				"  {\n" +
				"    \"resource\": \"DegradeRuleB\",\n" +
				"    \"count\": 5.0,\n" +
				"    \"grade\": 0,\n" +
				"    \"passCount\": 0,\n" +
				"    \"timeWindow\": 10\n" +
				"  }\n" +
				"]";
		ConfigService configService = NacosFactory.createConfigService(remoteAddress);
		log.info(String.valueOf(configService.publishConfig(dataId, groupId, rule)));
	}

	/**
	 * 添加流量控制配置参数
	 */
	public static void setSystemRule() throws Exception {
		final String remoteAddress = "118.184.218.184:8848";
		final String groupId = "Sentinel:Demo";
		final String dataId = "com.alibaba.csp.sentinel.demo.system.rule";
		final String rule = "[\n" +
				"  {\n" +
				"    \"avgRt\": 10,\n" +
				"    \"highestSystemLoad\": 5.0,\n" +
				"    \"maxThread\": 10,\n" +
				"    \"qps\": 20.0\n" +
				"  }\n" +
				"]";
		ConfigService configService = NacosFactory.createConfigService(remoteAddress);
		log.info(String.valueOf(configService.publishConfig(dataId, groupId, rule)));
	}

	/**
	 * 添加热点配置参数
	 */
	public static void setParamFlowRule() throws Exception {
		final String remoteAddress = "118.184.218.184:8848";
		final String groupId = "Sentinel:Demo";
		final String dataId = "com.alibaba.csp.sentinel.demo.param.rule";
		final String rule = "[{\n" +
				"\t\"clusterMode\": false,\n" +
				"\t\"count\": 5.0,\n" +
				"\t\"grade\": 1,\n" +
				"\t\"limitApp\": \"default\",\n" +
				"\t\"paramFlowItemList\": [{\n" +
				"\t\t\"classType\": \"int\",\n" +
				"\t\t\"count\": 10,\n" +
				"\t\t\"object\": \"2\"\n" +
				"\t}],\n" +
				"\t\"paramIdx\": 0,\n" +
				"\t\"resource\": \"ParamFlowA\"\n" +
				"}]";
		ConfigService configService = NacosFactory.createConfigService(remoteAddress);
		log.info(String.valueOf(configService.publishConfig(dataId, groupId, rule)));
	}

}

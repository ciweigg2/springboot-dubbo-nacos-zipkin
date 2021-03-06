/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mxc.web;

import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

import static org.springframework.core.env.StandardEnvironment.SYSTEM_ENVIRONMENT_PROPERTY_SOURCE_NAME;
import static org.springframework.core.env.StandardEnvironment.SYSTEM_PROPERTIES_PROPERTY_SOURCE_NAME;

/**
 * Dubbo Registry Nacos Consumer Bootstrap
 */
//@EnableAutoConfiguration
@SpringBootApplication
@NacosPropertySource(
        name = "custom",
        dataId = "nacos_config",
        first = true,
        groupId = "nacos_group",
        autoRefreshed = true,
        before = SYSTEM_PROPERTIES_PROPERTY_SOURCE_NAME,
        after = SYSTEM_ENVIRONMENT_PROPERTY_SOURCE_NAME
)
@ImportResource(value = "/dubbo-zipkin-consumer.xml")
public class DubboRegistryNacosConsumerBootstrap {

    public static void main(String[] args) {
        SpringApplication.run(DubboRegistryNacosConsumerBootstrap.class ,args);
    }
}

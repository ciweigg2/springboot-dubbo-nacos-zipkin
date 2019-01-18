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
package com.mxc.service.provider1;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.dubbo.rpc.RpcContext;
import com.mxc.service.DemoService;
import com.mxc.service.UserService;
import com.mxc.service.provider1.sentinel.ExceptionUtil;
import org.springframework.beans.factory.annotation.Value;

/**
 * Default {@link DemoService}
 *
 * @see DemoService
 * @since 1.0.0
 */
@Service(version = "${demo.service.version}")
public class DemoServiceImpl implements DemoService {

    @Reference(version = "${demo.service.version}")
    private UserService userService;

    /**
     * The default value of ${dubbo.application.name} is ${spring.application.name}
     */
    @Value("${dubbo.application.name}")
    private String serviceName;

    @Override
    @SentinelResource(value = "TestResource", blockHandler = "handleException", blockHandlerClass = {ExceptionUtil.class})
    public String sayHello(String name) {
        userService.sayName();
        System.out.println(userService.sayName());
        RpcContext rpcContext = RpcContext.getContext();
        System.out.println(String.format("Service [name :%s , port : %d] %s(\"%s\") : Hello,%s",
                serviceName,
                rpcContext.getLocalPort(),
                rpcContext.getMethodName(),
                name,
                name));
        return String.format("[%s] : Hello, %s", serviceName, name);
    }
}
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
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.fescar.spring.annotation.GlobalTransactional;
import com.mxc.service.DemoService;
import com.mxc.service.UserService;
import com.mxc.service.exception.ServiceException;
import com.mxc.service.provider1.mapper.StorageTblMapper;
import com.mxc.service.provider1.model.StorageTbl;
import com.mxc.service.status.EnumBaseError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;

/**
 * Default {@link DemoService}
 *
 * @see DemoService
 * @since 1.0.0
 */
@Service(version = "${demo.service.version}")
@Transactional(rollbackFor = Exception.class)
public class DemoServiceImpl implements DemoService {

    @Reference(version = "${demo.service.version}")
    private UserService userService;

    @Autowired
    private StorageTblMapper storageTblMapper;

    /**
     * The default value of ${dubbo.application.name} is ${spring.application.name}
     */
    @Value("${dubbo.application.name}")
    private String serviceName;

    @Override
//    @SentinelResource(value = "FlowRuleA",blockHandler = "exceptionHandler")
    // 对应的 `handleException` 函数需要位于 `ExceptionUtil` 类中，并且必须为 static 函数.
    @SentinelResource(value = "DegradeRuleA", blockHandler = "exceptionHandler", fallback = "helloFallback")
//    @SentinelResource(value = "FlowRuleA", blockHandler = "handleException", blockHandlerClass = {ExceptionUtil.class})
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

    @Override
    @GlobalTransactional(name = "DemoService.addDemo", timeoutMills = 60000)
    public void addDemo() {
        StorageTbl storageTbl = new StorageTbl();
        storageTbl.setCommodityCode(String.valueOf(System.currentTimeMillis()));
        storageTbl.setCount(2);
        storageTblMapper.insert(storageTbl);
        userService.addUser();
        throw new ServiceException(EnumBaseError.MISSING_PARAMETERS);
//        throw new RuntimeException("asd");
    }

    // Fallback 函数，函数签名与原函数一致 fallback只有降级的时候才会触发 业务异常不会进入 fallback 逻辑
    public String helloFallback(String name) {
        return String.format("Halooooo %s", name);
    }

    // Block 异常处理函数，参数最后多一个 BlockException，其余与原函数一致.
    public String exceptionHandler(String name, BlockException ex) {
        return "不好意思当前太挤啦，Oops, error occurred at " + name;
    }

}
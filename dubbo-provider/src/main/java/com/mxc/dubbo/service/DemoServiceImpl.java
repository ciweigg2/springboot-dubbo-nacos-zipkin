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
package com.mxc.dubbo.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.fescar.core.context.RootContext;
import com.alibaba.fescar.spring.annotation.GlobalTransactional;
import com.mxc.dubbo.DemoService;
import com.mxc.dubbo.UserService;

import com.mxc.dubbo.common.status.EnumBaseError;
import com.mxc.dubbo.mapper.StorageTblMapper;
import com.mxc.dubbo.model.StorageTbl;
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
        System.out.println("事务id：" + RootContext.getXID());
        StorageTbl storageTbl = new StorageTbl();
        storageTbl.setCommodityCode(String.valueOf(System.currentTimeMillis()));
        storageTbl.setCount(2);
        storageTblMapper.insert(storageTbl);
        userService.addUser();
    }

}
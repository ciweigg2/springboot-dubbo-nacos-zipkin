package com.mxc.dubbo.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.nacos.api.config.annotation.NacosConfigListener;
import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.mxc.dubbo.DemoService;
import com.mxc.dubbo.UserService;

import com.mxc.dubbo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DubboController {

    @Reference(version = "${demo.service.version}")
    private DemoService demoService;

    @Reference(version = "${demo.service.version}")
    private UserService userService;

    @NacosValue(value = "${name:unknown}" ,autoRefreshed = true)
    private String name;

    @Autowired
    private User user;

    @RequestMapping(value = "/sayHello")
    public String dubboSayHello(){
        System.out.println(name);
        return demoService.sayHello("sayHello");
    }

    /**
     * 测试分布式事务
     */
    @RequestMapping(value = "/addDemo")
    public void addUser() {
        demoService.addDemo();
    }

    //监听nacos配置文件的变化
    @NacosConfigListener(
            dataId = "nacos_config",
            groupId = "nacos_group",
            timeout = 500
    )
    public void onChange(String newContent) throws Exception {
        System.out.println("onChange : " + newContent);
    }

    @NacosConfigListener(
            dataId = "nacos_config2",
            groupId = "nacos_group",
            timeout = 500
    )
    public void onChange2(String newContent) throws Exception {
        System.out.println("onChange : " + newContent);
    }

}
package com.mxc.service.provider2;

import com.alibaba.dubbo.config.annotation.Service;
import com.mxc.service.UserService;

@Service(version = "${demo.service.version}")
public class UserServiceImpl implements UserService {

    @Override
    public String sayName() {
        return "小马大神呀";
    }

}

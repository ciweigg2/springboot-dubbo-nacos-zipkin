package com.mxc.dubbo.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fescar.core.context.RootContext;
import com.mxc.dubbo.UserService;

import com.mxc.dubbo.mapper.UserTblMapper;
import com.mxc.dubbo.model.UserTbl;
import org.springframework.beans.factory.annotation.Autowired;

@Service(version = "${demo.service.version}")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserTblMapper userTblMapper;

    @Override
    public String sayName() {
        return "小马大神呀";
    }

    @Override
//    @GlobalTransactional(name = "UserService.addUser", timeoutMills = 60000)
    public void addUser() {
        System.out.println("事务id：" + RootContext.getXID());
        UserTbl userTbl = new UserTbl();
        userTbl.setUsername("test");
        userTbl.setPassword("test");
        userTbl.setAge(99);
        userTblMapper.insert(userTbl);
        int m = 1/0;
    }

}

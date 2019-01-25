package com.mxc.service.provider2;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fescar.spring.annotation.GlobalTransactional;
import com.mxc.service.UserService;
import com.mxc.service.exception.ServiceException;
import com.mxc.service.provider2.mapper.UserTblMapper;
import com.mxc.service.provider2.model.UserTbl;
import com.mxc.service.status.EnumBaseError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Service(version = "${demo.service.version}")
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl implements UserService {

    @Autowired
    private UserTblMapper userTblMapper;

    @Override
    public String sayName() {
        return "小马大神呀";
    }

    @Override
    @GlobalTransactional(name = "UserService.addUser", timeoutMills = 60000)
    public void addUser() {
        UserTbl userTbl = new UserTbl();
        userTbl.setUsername("test");
        userTbl.setPassword("test");
        userTbl.setAge(99);
        userTblMapper.insert(userTbl);
        throw new ServiceException(EnumBaseError.MISSING_PARAMETERS);
    }

}

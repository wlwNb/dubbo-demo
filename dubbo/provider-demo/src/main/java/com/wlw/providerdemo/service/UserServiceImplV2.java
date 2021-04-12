package com.wlw.providerdemo.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.wlw.common.dto.UserDto;
import com.wlw.common.interfaces.UserService;
import com.wlw.common.mapstruct.UserMapper;
import com.wlw.common.vo.ResultVo;
import com.wlw.common.entity.User;
import com.wlw.common.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

/**
 * @Author: wlw 910138
 * @Date: 2019/9/23 16:38
 */
@Slf4j
@Service(version = "2.0.0")
public class UserServiceImplV2 implements UserService {

    @Value("${server.port}")
    private int port;

    @Autowired
    private UserMapper userMapper;

    @Override
    public String getUser(Long userId) {
        log.info("this is provider service,userId:" + userId + ",port:" + port);
        return "this is provider service,userId:" + userId + ",port:" + port;
    }

    @Override
    public ResultVo<User> getUserV1(String name) {
        // dubbo超时配置
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        User user = new User();
        user.setName(name);
        user.setPassword("UserServiceImplV2 passwordV1");
        return ResultVo.success(user);
    }

    @Override
    public ResultVo getUserV2(String name) {
        User user = new User();
        user.setName(name);
        user.setPassword("UserServiceImplV2 passwordV2");
        return ResultVo.success(user);
    }

    @Override
    public ResultVo getUserV3(UserVo userVo) {
        UserDto dto = userMapper.convert(userVo);
        User user = userMapper.convert(dto);
        return ResultVo.success(user);
    }

    @Override
    public ResultVo getUserV4(UserVo userVo) {
        UserDto dto = userMapper.convert(userVo);
        User user = userMapper.convert(dto);
        return ResultVo.success(user);
    }

}
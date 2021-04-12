package com.wlw.common.interfaces;


import com.wlw.common.entity.User;
import com.wlw.common.vo.ResultVo;
import com.wlw.common.vo.UserVo;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @Author: wlw 910138
 * @Date: 2019/9/23 15:51
 */
public interface UserService {

    String getUser(Long userId);

    ResultVo<User> getUserV1(String name);

    ResultVo getUserV2(String name);

    ResultVo getUserV3(UserVo userVo);

    ResultVo getUserV4(@RequestBody UserVo userVo);

}
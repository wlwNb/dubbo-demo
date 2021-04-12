package com.wlw.common.mapstruct;

import com.wlw.common.dto.UserDto;
import com.wlw.common.entity.User;
import com.wlw.common.vo.UserVo;
import org.mapstruct.Mapper;

/**
 * @Author: wlw
 * @Date: 2020/11/26 14:46
 */
@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto convert(UserVo userVo);

    User convert(UserDto userDto);

}
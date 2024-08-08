package com.anonymous63.crs.services;

import com.anonymous63.crs.dtos.UserDto;

public interface UserService extends CrudService<UserDto> {
    UserDto register(UserDto userDto);
}

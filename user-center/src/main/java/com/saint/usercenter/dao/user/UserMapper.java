package com.saint.usercenter.dao.user;

import com.saint.usercenter.domain.entity.user.User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface UserMapper extends Mapper<User> {
}
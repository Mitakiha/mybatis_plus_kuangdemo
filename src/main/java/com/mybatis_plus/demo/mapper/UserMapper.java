package com.mybatis_plus.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mybatis_plus.demo.pojo.User;
import org.springframework.stereotype.Repository;
//在对应的Mapper上面继承基本的类，BaseMapper
@Repository  //代表持久层
public interface UserMapper extends BaseMapper<User> {
}

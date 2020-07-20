package com.mybatis_plus.demo;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mybatis_plus.demo.mapper.UserMapper;
import com.mybatis_plus.demo.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

@SpringBootTest
public class WrapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void contextLoads(){
        //查询name不能为空的用户，并且邮箱不为空的用户，年龄大于等于10岁
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.isNotNull("name")
                .isNotNull("email")
                .ge("age",12);
        userMapper.selectList(wrapper).forEach(System.out::println);//注意与map对比
    }

    @Test
    public void test2(){
        //查询名字是kkk
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("name","kkk");
        User user = userMapper.selectOne(wrapper);
        System.out.println(user);
    }

    @Test
    public void test3(){
        //查询年龄在10~20的用户
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.between("age",10,20);
        Integer count = userMapper.selectCount(wrapper);
        System.out.println(count);
    }

    //模糊查询
    @Test
    public void test4(){
        //查询名字中有hhh和邮箱是以62开头的用户
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        //左和右 t%
        wrapper.like("name","hhh")
                .likeRight("email","62");

        List<Map<String, Object>> maps = userMapper.selectMaps(wrapper);
        maps.forEach(System.out::println);
    }

    //模糊查询
    @Test
    public void test5(){

        QueryWrapper<User> wrapper = new QueryWrapper<>();
        //id 在子查询中查出来
        wrapper.inSql("id","select id from user where id<3");

        List<Object> objects = userMapper.selectObjs(wrapper);
        objects.forEach(System.out::println);
    }

    //排序
    @Test
    public void test6(){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        //通过age进行排序
        wrapper.orderByAsc("age");

        List<User> users = userMapper.selectList(wrapper);
        users.forEach(System.out::println);
    }
}

package com.mybatis_plus.demo;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mybatis_plus.demo.mapper.UserMapper;
import com.mybatis_plus.demo.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@SpringBootTest
class DemoApplicationTests {

	@Autowired
	private UserMapper userMapper;

	@Test
	void contextLoads() {

		System.out.println("hello world!");
		List<User> users = userMapper.selectList(null);
		users.forEach(System.out::println);
	}

//	插入信息

	@Test
	public void testInsert(){


		User user1 = new User();
		user1.setAge(18);
		user1.setEmail("52154@qq.com");
		user1.setName("hhh哈哈哈");
		int insert = userMapper.insert(user1);

		System.out.println(insert);
		System.out.println(user1);
	}

	@Test
	public void updateInsert(){


		User user1 = new User();
		user1.setId(11L);
		user1.setEmail("52154@qq.com");
		user1.setName("hhh呵呵呵");
		int i = userMapper.updateById(user1);

		System.out.println(i);
		System.out.println(user1);
	}

	//乐观锁成功
	@Test
	public void testOptimisticLocker(){
		//1 查询用户信息
		User user = userMapper.selectById(1L);
		//2 修改用户信息
		user.setName("Mitaki");
		user.setEmail("271717509@qq.com");
		//3 执行更新操作
		userMapper.updateById(user);
	}

	//测试乐观锁失败！多线程下
	@Test
	public void testOptimisticLocker2(){
		//1 查询用户信息
		User user = userMapper.selectById(1L);
		//2 修改用户信息
		user.setName("Mitaki");
		user.setEmail("271717509@qq.com");

		//插队

		User user2 = userMapper.selectById(1L);
		user2.setName("插队人员");
		user2.setEmail("22222@qq.com");
		userMapper.updateById(user2);
		//3 执行更新操作
		//可使用自旋锁来多次提交
		userMapper.updateById(user);//如果没有乐观锁，就会覆盖插队线程的值
	}

	//查询
	@Test
	public void testSelectById(){
		User user = userMapper.selectById(1L);
		System.out.println(user);
	}

	//查询多组
	@Test
	public void testSelectByBatchId(){
		List<User> users = userMapper.selectBatchIds(Arrays.asList(1,2,3));
		users.forEach(System.out::println);

	}

	//按条件查询之一 使用map操作
	@Test
	public void testSelectByBatchIds(){
		HashMap<String, Object> map = new HashMap<>();
		//自定义查询
		map.put("name","hhh呵呵呵");

		List<User> users = userMapper.selectByMap(map);
		users.forEach(System.out::println);

	}


	//测试分页插件
	@Test
	public void testPage(){
		//参数一：当前页
		//参数二：页面大小
		Page<User> page = new Page<>(1,5);
		userMapper.selectPage(page,null);

		page.getRecords().forEach(System.out::println);
	}


	//测试删除  配置逻辑删除：本质走的是更新操作，并不是删除操作，数据库数据还在
	@Test
	public void testDeleteById(){
		userMapper.deleteById(2L);
	}
	//通过id批量删除
	@Test
	public void testDeleteByIds(){
		userMapper.deleteBatchIds(Arrays.asList(2,3,4L));
	}
	//通过map删除
	@Test
	public void testDeleteMap(){
		HashMap<String, Object> map = new HashMap<>();
		map.put("name","hhh呵呵呵");
		userMapper.deleteByMap(map);
	}
}

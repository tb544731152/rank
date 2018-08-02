package com.imooc.miaosha.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.imooc.miaosha.domain.User;

@Mapper
public interface UserDao {
	
	@Select("select * from user where id = #{id}")
	public User getById(@Param("id")int id	);

	@Insert("insert into user(id, name)values(#{id}, #{name})")
	public int insert(User user);
	@Select("select count(*) from oo_fans")
	public int sdselect();
	
	@Select("select count(*) from oo_fans")
	public int select();
	
	@Select("select nickname from oo_fans  where zyzsId = #{zyzsId}")
	public String selectNickName(String zyzsId);
	
	
}

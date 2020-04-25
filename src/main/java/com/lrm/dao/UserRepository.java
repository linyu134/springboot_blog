package com.lrm.dao;

import com.lrm.po.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {

    //根据用户名和密码去查询数据库
    User findByUsernameAndPassword(String username,String password);
}

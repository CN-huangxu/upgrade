package com.example.demo1.mapper;


import com.example.demo1.pojo.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {
 public List<User> selectUserList();
}

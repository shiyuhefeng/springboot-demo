package com.study.springboot.server;

import com.study.springboot.common.Result;
import com.study.springboot.entity.User;
import org.springframework.web.bind.annotation.RequestBody;

public interface UserService {

    public Result<?> saveUser(User user);

    public Result<?> findPageUser(Integer pageNo, Integer pageSize, String search);

    public Result<?> updateUserById(User user);

    public Result<?> deleteUserById(long id);

    public Result<?> userLogin(User user);

    public Result<?> selectById(long id);

    public Result<?> userRegister(User user);

}

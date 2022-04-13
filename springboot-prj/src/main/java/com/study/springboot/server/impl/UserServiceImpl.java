package com.study.springboot.server.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.study.springboot.common.Result;
import com.study.springboot.entity.User;
import com.study.springboot.mapper.UserMapper;
import com.study.springboot.server.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userDao;

    @Override
    public Result<?> saveUser(User user) {
        if(user.getPassword() == null) {
            user.setPassword("123456");
        }
        userDao.insert(user);
        return Result.success();
    }

    @Override
    public Result<?> findPageUser(Integer pageNo, Integer pageSize, String search) {
        LambdaQueryWrapper<User> wrapper = Wrappers.<User>lambdaQuery();
        if(StrUtil.isNotBlank(search)) {
            wrapper.like(User::getNickName, search);
        }
        Page<User> userPage = userDao.findPage(new Page<>(pageNo, pageSize));
        return Result.success(userPage);
    }

    @Override
    public Result<?> updateUserById(User user) {
        userDao.updateById(user);
        return Result.success();
    }

    @Override
    public Result<?> deleteUserById(long id) {
        userDao.deleteById(id);
        return Result.success();
    }

    @Override
    public Result<?> userLogin(User user) {
        User res = userDao.selectOne(Wrappers.<User>lambdaQuery().eq(User::getUsername, user.getUsername()).eq(User::getPassword, user.getPassword()));
        if(res == null) {
            return Result.error("1", "用户名或密码错误");
        }
        return Result.success(res);
    }

    @Override
    public Result<?> userRegister(User user) {
        // 校验用户名是否存在
        User res = userDao.selectOne(Wrappers.<User>lambdaQuery().eq(User::getUsername, user.getUsername()));
        if(res != null) {
            return Result.error("1", "用户名存在");
        }
        if(user.getPassword() == null) {
            user.setPassword("123456");
        }
        userDao.insert(user);
        return Result.success();
    }

    @Override
    public Result<?> selectById(long id) {
        final User user = userDao.selectById(id);
        return Result.success(user.getRole());
    }

}

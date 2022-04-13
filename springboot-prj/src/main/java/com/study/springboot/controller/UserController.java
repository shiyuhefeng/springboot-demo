package com.study.springboot.controller;

import com.study.springboot.common.Result;
import com.study.springboot.entity.User;
import com.study.springboot.server.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 新增用户
     */
    @PostMapping
    public Result<?> save(@RequestBody User user) {
        return userService.saveUser(user);
    }

    /**
     * 分页查询
     */
    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNo,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search) {
        return userService.findPageUser(pageNo, pageSize, search);
    }

    /**
     * 更新用户信息
     */
    @PutMapping
    public Result<?> updateUser(@RequestBody User user) {
        return userService.updateUserById(user);
    }

    /**
     * 根据用户ID进行查询
     */
    @GetMapping("{id}")
    public Result<?> selectUserById(@PathVariable long id) {
        return userService.selectById(id);
    }

    /**
     * 删除用户
     */
    @DeleteMapping("/{id}")
    public Result<?> deleteUser(@PathVariable long id) {
        return userService.deleteUserById(id);
    }

    /**
     * 登录验证
     */
    @PostMapping("/login")
    public Result<?> login(@RequestBody User user) {
        return userService.userLogin(user);
    }

    /**
     * 注册
     */
    @PostMapping("/register")
    public Result<?> register(@RequestBody User user) {
        return userService.userRegister(user);
    }

}

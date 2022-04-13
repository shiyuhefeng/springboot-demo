package com.study.springboot.controller;

import com.study.springboot.common.Result;
import com.study.springboot.server.CategoryService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Resource
    private CategoryService categoryService;

    /**
     * 获得所有的子节点
     */
    @GetMapping
    public Result<?> getAll() {
        return categoryService.selectList();
    }

    /**
     * 删除
     */
    @DeleteMapping("/{id}")
    public Result<?> deleteById(@PathVariable long id) {
        return categoryService.deleteById(id);
    }
}
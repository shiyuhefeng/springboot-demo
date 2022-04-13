package com.study.springboot.server;

import com.study.springboot.common.Result;
import com.study.springboot.entity.Category;

import java.util.List;

public interface CategoryService {

    public Result<?> selectList();

    public List<Category> loopQuery(Integer pid, List<Category> allCategories);

    public Result<?> deleteById(long id);
}

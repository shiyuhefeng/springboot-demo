package com.study.springboot.server.impl;

import com.study.springboot.common.Result;
import com.study.springboot.entity.Category;
import com.study.springboot.mapper.CategoryMapper;
import com.study.springboot.server.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Resource
    private CategoryMapper categoryMapper;

    /**
     * 分类父子查询
     */
    public Result<?> selectList() {
        // 先查询所有的数据
        List<Category> allCategory = categoryMapper.selectList(null);
        return Result.success(loopQuery(null, allCategory));
    }

    @Override
    public List<Category> loopQuery(Integer pid, List<Category> allCategories) {
        List<Category> categoryList = new ArrayList<>();
        for(Category category : allCategories) {
            if(pid == null) {
                if(category.getPid() == null) {
                    categoryList.add(category);
                    category.setChildren(loopQuery(category.getId(), allCategories));
                }
            }else if(pid.equals(category.getPid())) {
                categoryList.add(category);
                category.setChildren(loopQuery(category.getId(), allCategories));
            }
        }
        return categoryList;
    }

    @Override
    public Result<?> deleteById(long id) {
        categoryMapper.deleteById(id);
        return Result.success();
    }
}

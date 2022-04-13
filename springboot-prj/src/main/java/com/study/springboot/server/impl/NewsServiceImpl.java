package com.study.springboot.server.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.study.springboot.common.Result;
import com.study.springboot.entity.News;
import com.study.springboot.mapper.NewsMapper;
import com.study.springboot.server.NewsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class NewsServiceImpl implements NewsService {

    @Resource
    private NewsMapper newsDao;

    @Override
    public Result<?> selectAllNews(Integer pageNo, Integer pageSize, String search) {
        LambdaQueryWrapper<News> wrapper = Wrappers.<News>lambdaQuery();
        // 如果查询内容不为空  则进行模糊查询
        if(StrUtil.isNotBlank(search)) {
            wrapper.like(News::getTitle, search);
        }
        Page<News> newsPage = newsDao.selectPage(new Page<>(pageNo, pageSize), wrapper);
        return Result.success(newsPage);
    }

    @Override
    public Result<?> addNewNews(News news) {
        newsDao.insert(news);
        return Result.success();
    }

    @Override
    public Result<?> updateNewsMessage(News news) {
        newsDao.updateById(news);
        return Result.success();
    }

    @Override
    public Result<?> deleteNewsById(long id) {
        newsDao.deleteById(id);
        return Result.success();
    }
}

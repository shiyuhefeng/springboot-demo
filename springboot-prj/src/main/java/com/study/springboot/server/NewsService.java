package com.study.springboot.server;

import com.study.springboot.common.Result;
import com.study.springboot.entity.News;

public interface NewsService {

    // 查询
    public Result<?> selectAllNews(Integer pageNo, Integer pageSize, String search);

    // 新增
    public Result<?> addNewNews(News news);

    // 修改信息
    public Result<?> updateNewsMessage(News news);

    // 删除书籍
    public Result<?> deleteNewsById(long id);

}

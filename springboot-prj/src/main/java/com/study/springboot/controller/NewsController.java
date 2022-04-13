package com.study.springboot.controller;

import com.study.springboot.common.Result;
import com.study.springboot.entity.Book;
import com.study.springboot.entity.News;
import com.study.springboot.server.BookService;
import com.study.springboot.server.NewsService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;

@RestController
@RequestMapping("/news")
public class NewsController {

    @Resource
    private NewsService newsService;

    @GetMapping
    public Result<?> selectAllNews(@RequestParam(defaultValue = "1") Integer pageNo,
                                    @RequestParam(defaultValue = "10") Integer pageSize,
                                    @RequestParam(defaultValue = "") String search) {
        return newsService.selectAllNews(pageNo, pageSize, search);
    }

    /**
     * 新增书籍
     */
    @PostMapping
    public Result<?> save(@RequestBody News news) {
        news.setTime(new Date());
        return newsService.addNewNews(news);
    }

    /**
     * 更新用户信息
     */
    @PutMapping
    public Result<?> updateNews(@RequestBody News news) {
        return newsService.updateNewsMessage(news);
    }

    /**
     * 删除书籍
     */
    @DeleteMapping("/{id}")
    public Result<?> deleteNews(@PathVariable long id) {
        return newsService.deleteNewsById(id);
    }

}

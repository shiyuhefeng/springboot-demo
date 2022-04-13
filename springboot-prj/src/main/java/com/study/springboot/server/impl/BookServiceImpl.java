package com.study.springboot.server.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.study.springboot.common.Result;
import com.study.springboot.entity.Book;
import com.study.springboot.mapper.BooksMapper;
import com.study.springboot.server.BookService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    @Resource
    private BooksMapper booksDao;

    /**
     * 查询书籍信息
     */
    @Override
    public Result<?> selectAllBooks(Integer pageNo, Integer pageSize, String search) {
        LambdaQueryWrapper<Book> wrapper = Wrappers.<Book>lambdaQuery();
        // 如果查询内容不为空  则进行模糊查询
        if (StrUtil.isNotBlank(search)) {
            wrapper.like(Book::getBookName, search);
        }
        // 查询出集合  返回
        Page<Book> bookPage = booksDao.selectPage(new Page<>(pageNo, pageSize), wrapper);
        return Result.success(bookPage);
    }

    /**
     * 添加书
     */
    @Override
    public Result<?> addNewBook(Book book) {
        booksDao.insert(book);
        return Result.success();
    }

    /**
     * 更新信息
     */
    @Override
    public Result<?> updateBookMessage(Book book) {
        booksDao.updateById(book);
        return Result.success();
    }

    /**
     * 删除
     */
    @Override
    public Result<?> deleteUserById(long id) {
        booksDao.deleteById(id);
        return Result.success();
    }

    @Override
    public Result<?> deleteBatch(List<Integer> ids) {
        booksDao.deleteBatchIds(ids);
        return Result.success();
    }
}

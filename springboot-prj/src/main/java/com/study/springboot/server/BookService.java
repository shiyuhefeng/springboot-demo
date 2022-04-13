package com.study.springboot.server;

import com.study.springboot.common.Result;
import com.study.springboot.entity.Book;

import java.util.List;

public interface BookService {

    // 查询
    public Result<?> selectAllBooks(Integer pageNo, Integer pageSize, String search);

    // 新增
    public Result<?> addNewBook(Book book);

    // 修改信息
    public Result<?> updateBookMessage(Book book);

    // 删除书籍
    public Result<?> deleteUserById(long id);

    // 批量删除
    public Result<?> deleteBatch(List<Integer> ids);
}

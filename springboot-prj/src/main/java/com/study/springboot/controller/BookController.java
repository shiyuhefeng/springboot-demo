package com.study.springboot.controller;

import com.study.springboot.common.Result;
import com.study.springboot.entity.Book;
import com.study.springboot.server.BookService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    @Resource
    private BookService bookService;

    @GetMapping
    public Result<?> selectAllBooks(@RequestParam(defaultValue = "1") Integer pageNo,
                                    @RequestParam(defaultValue = "10") Integer pageSize,
                                    @RequestParam(defaultValue = "") String search) {
        return bookService.selectAllBooks(pageNo, pageSize, search);
    }

    /**
     * 新增书籍
     */
    @PostMapping
    public Result<?> save(@RequestBody Book book) {
        return bookService.addNewBook(book);
    }

    /**
     * 更新用户信息
     */
    @PutMapping
    public Result<?> updateBooks(@RequestBody Book book) {
        return bookService.updateBookMessage(book);
    }

    /**
     * 删除书籍
     */
    @DeleteMapping("/{id}")
    public Result<?> deleteBooks(@PathVariable long id) {
        return bookService.deleteUserById(id);
    }

    /**
     * 批量删除
     */
    @PostMapping("/deleteBatch")
    public Result<?> deleteBatch(@RequestBody List<Integer> ids) {
        return bookService.deleteBatch(ids);
    }

}

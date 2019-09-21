package com.check24.codingchallenge.bookstore.controller;

import com.check24.codingchallenge.bookstore.entity.Book;
import com.check24.codingchallenge.bookstore.payload.BookDto;
import com.check24.codingchallenge.bookstore.service.BookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * This Controller is used to manipulate books
 * Created by Bassem
 */
@RestController
@RequestMapping("/task/books")
@Api(tags = {"Book API"}, value = "Book API", description = "This API is used to manipulate books")
@CrossOrigin(origins = "http://localhost:4200")
public class BookController {
    @Autowired
    BookService bookService;

    @Autowired
    ModelMapper modelMapper;

    /**
     * @return
     */
    @ApiOperation(value = "This endpoint is user to get all books")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = ""),
            @ApiResponse(code = 400, message = "")
    })
    @GetMapping
    public ResponseEntity<?> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks().stream().map((b) -> modelMapper.map(b, BookDto.class)));
    }

    /**
     * @param bookId
     * @return
     */

    @ApiOperation(value = "This endpoint is used to get a certain book and update its views")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = ""),
            @ApiResponse(code = 400, message = "")
    })
    @PutMapping("/{bookId}")
    public ResponseEntity<?> viewABook(@PathVariable long bookId) {
        //TODO BASSEM: this part should be update by a way that the user id is taken from the token
        Book book = bookService.viewABook(bookId, 1);
        return ResponseEntity.ok(modelMapper.map(book, BookDto.class));
    }

    /**
     * @param bookId
     * @return
     */
    @ApiOperation(value = "This endpoint is used to find the similar books to the current book")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = ""),
            @ApiResponse(code = 400, message = "")
    })
    @GetMapping("/{bookId}/similarBooks")
    public ResponseEntity<?> findSimilarBooks(@PathVariable long bookId) {
        List<BookDto> books = bookService.findSimilarBooks(bookId).stream().map((b) -> modelMapper.map(b, BookDto.class)).collect(Collectors.toList());
        return ResponseEntity.ok(books);
    }


}

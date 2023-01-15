package getir.controller;

import getir.entity.Book;
import getir.service.IBookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/books")
@RequiredArgsConstructor
public class BookController {

    private final IBookService bookService;

    @PostMapping("/create")
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        try {
            Book createdBook = bookService.createBook(book);

            if (createdBook != null) {
                log.info("book is created with id ={}", createdBook.getId());
                return new ResponseEntity<>(createdBook, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/updateStock")
    public ResponseEntity<Book> updateBookStock(@RequestBody Book book) {
        try {
            Book updatedBook = bookService.updateBookStock(book.getId(), book.getStock());

            if (updatedBook != null) {
                log.info("book is updated with id ={}", book.getId());
                return new ResponseEntity<>(updatedBook, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

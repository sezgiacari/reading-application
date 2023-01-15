package getir.service;

import getir.entity.Book;
import getir.repository.IBookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookService implements IBookService {
    private final IBookRepository bookRepository;

    @Override
    public Book createBook(Book book) {

        //find if books exist.
        Optional<Book> bookExists = bookRepository.findByNameAndWriterAndPrice(book.getName(), book.getWriter(), book.getPrice());

        Book bookRecord = new Book();
        // if book is exist update stock.
        if (bookExists.isPresent()) {
            bookRecord = bookExists.get();
            bookRecord.setUpdatedAt(LocalDateTime.now());
            bookRecord.setStock(book.getStock() + bookExists.get().getStock());

        } else { // if not create a new book.
            bookRecord.setName(book.getName());
            bookRecord.setWriter(book.getWriter());
            bookRecord.setStock(book.getStock());
            bookRecord.setPrice(book.getPrice());
        }
        Book savedBook = bookRepository.save(bookRecord);
        log.info("New Book is added successfully");

        return savedBook;
    }

    @Override
    public Book updateBookStock(String bookId, Integer stockCount) {
       //check if book exist.
        Optional<Book> existBook = bookRepository.findById(bookId);

        if (existBook.isEmpty()) {
            log.error("Book not found : id={}", bookId);
            return null;
        }
        // update book
        Book updateBook = existBook.get();
        updateBook.setStock(stockCount);
        Book updatedBook = bookRepository.save(updateBook);

        log.info("Book is updated successfully id={}", bookId);
        return updatedBook;
    }


    @Override
    public Book updateOrderedBookCount(String bookId) {
        final Optional<Book> book = bookRepository.findById(bookId);
        if (book.isEmpty()) {
            log.error("book is not found id={}", bookId);
            return null;
        }
        book.get().setStock(book.get().getStock() - 1);
        return bookRepository.save(book.get());
    }

    @Override
    public void saveBook(Book book) {
        bookRepository.save(book);
    }

    @Override
    public Book checkBooksStock(String bookId) {
        Optional<Book> bookOpt = bookRepository.findById(bookId);

        if (bookOpt.isEmpty()) {
            log.error("Book is not found with bookId={}", bookId);
            return null;
        } else if (bookOpt.get().getStock() > 0) {
            log.error("Book does have enough stock with bookId={} existingStock={}",
                    bookId, bookOpt.get().getStock());
        }
        return bookOpt.get();
    }
}

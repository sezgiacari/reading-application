package getir.service;

import getir.entity.Book;

public interface IBookService {
    Book createBook(Book bookAddRequest);
    Book updateBookStock(String bookId, Integer stockCount);
    void saveBook(Book book);
    Book checkBooksStock(String bookId);
    Book updateOrderedBookCount(String bookId);
}

import getir.entity.Book;
import getir.repository.IBookRepository;
import getir.service.BookService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class BookServiceTest {

    @InjectMocks
    private BookService bookService;

    @Mock
    private IBookRepository bookRepository;


    @Test
    void addOrUpdateBook() {

        String name = "Test Book";
        String writer = "Test Writer";
        Integer stock = 100;
        Double price = 10.5;

        Book book = new Book();
        book.setName(name);
        book.setWriter(writer);
        book.setStock(stock);
        book.setPrice(price);

        when(bookRepository.findByNameAndWriterAndPrice(name, writer, price))
                .thenReturn(Optional.empty());

        when(bookRepository.save(any(Book.class))).thenReturn(book);

        Book bookDto = bookService.createBook(book);

        assertNotNull(bookDto);
        assertEquals(bookDto.getName(), book.getName());
        assertEquals(bookDto.getPrice(), book.getPrice());


    }
}

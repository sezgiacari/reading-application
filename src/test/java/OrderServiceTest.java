
import getir.entity.Book;
import getir.entity.Order;
import getir.repository.IOrderRepository;
import getir.service.IBookService;
import getir.service.OrderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;


import java.util.List;


@ExtendWith(SpringExtension.class)
class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private IOrderRepository orderRepository;

    @Mock
    private IBookService bookService;

    @Test
    void createNewOrder() {

        String customerId = "c101L";
        Book bookId1 = new Book();
        bookId1.setId("1");


        List<Book> bookList = new ArrayList<>();
        bookList.add(bookId1);


        Order order = new Order();
        order.setCustomerId(customerId);
        order.setBookList(bookList);


        Order orderCreate = orderService.createNewOrder(order);

       /* assertNotNull(orderCreate);
        assertEquals(customerId, orderCreate.getCustomerId());
        assertEquals(bookList.size(), orderCreate.getBookList().size());*/
    }
}

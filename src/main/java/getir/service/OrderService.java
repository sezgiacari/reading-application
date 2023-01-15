package getir.service;

import getir.entity.Book;
import getir.entity.Order;
import getir.repository.IBookRepository;
import getir.repository.IOrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderService implements IOrderService {

    private final IOrderRepository orderRepository;
    private final IBookService bookService;
    private final ReentrantLock reentrantLock = new ReentrantLock();
    private final IBookRepository bookRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Order createNewOrder(Order newOrder) { //todo

        try {
            if (reentrantLock.tryLock(10, TimeUnit.SECONDS)) {
                String customerId = newOrder.getCustomerId();
                List<Book> bookList = newOrder.getBookList();

                // fetch books by id and controlling whether or not there is enough stock for each book order
                List<Book> bookListCheck = bookList.stream()
                        .map(bookOrder -> bookService.checkBooksStock(bookOrder.getId()))
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList());

                if (bookListCheck.size() < bookList.size()) {
                    log.error("There is no stock for this order...");
                    return null;
                }

                // calculate total order price
                List<Book> books = newOrder.getBookList();


                Double totalPrice = 0.0;
                Integer totalBook =  bookListCheck.size();
                for (Book b : bookListCheck) {
                    totalPrice += b.getPrice();
                }
                newOrder.setTotalBooks(totalBook);
                newOrder.setTotalPrice(totalPrice);

                Order createdOrder = orderRepository.save(newOrder);
                if (!createdOrder.getId().isEmpty()){
                    //Order created update book stock.
                    for (Book sb : createdOrder.getBookList()){
                            bookService.updateOrderedBookCount(sb.getId());
                    }
                }
                log.info("Order is created successfully with customer={} books={}", customerId, createdOrder.getBookList());

                return createdOrder;
            }
        } catch (Exception e) {
            log.error("Order is not created error{}", e.getMessage());
            throw new RuntimeException("Create new order is failed");
        } finally {
            reentrantLock.unlock();
        }
        return null;
    }

    @Override
    public Order getOrderById(String orderId) {
        final Optional<Order> order = orderRepository.findById(orderId);
        if (order.isEmpty()) {
            log.error("Order is not found with id={}", orderId);
            return null;
        }
        return order.get();
    }

    @Override
    public List<Order> getOrdersByDate(LocalDateTime startDate, LocalDateTime endDate) {
        List<Order> orders = orderRepository.findByCreatedAtBetween(startDate, endDate);
        return orders;
    }

    @Override
    public List<Order> getOrdersByCustomerId(String customerId, Pageable pageable) {
        Page<Order> pageOrders = orderRepository.findByCustomerId(customerId, pageable);
        List<Order> customerOrders = pageOrders.getContent();
        return customerOrders;
    }
}

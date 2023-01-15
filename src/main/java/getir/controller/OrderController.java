package getir.controller;

import getir.entity.Order;
import getir.service.IOrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.format.annotation.DateTimeFormat.ISO.DATE;

@Slf4j
@RestController
@RequestMapping(value = "/order", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class OrderController {

    private final IOrderService orderService;

    @PostMapping("/create")
    public ResponseEntity<Order> createOrder(@RequestBody Order newOrder) {
        try {
            Order order = orderService.createNewOrder(newOrder);
            if (order != null) {
                return new ResponseEntity<>(order, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
        } catch (OptimisticLockingFailureException ex) {
            log.error("Concurrent update is detected. Please try again");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            log.error("Exception is occurred during order creation {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrder(@PathVariable String id) {

        log.info("Get order id={}", id);

        try {
            Order orderDto = orderService.getOrderById(id);
            if (orderDto == null) {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(orderDto, HttpStatus.OK);

        } catch (Exception ex) {
            log.error("Exception is occurred during order fetching ", ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping()
    public ResponseEntity<List<Order>> getOrdersByDateInterval(@DateTimeFormat(iso = DATE) @RequestParam LocalDate startDate,
                                                               @DateTimeFormat(iso = DATE) @RequestParam LocalDate endDate) {

        log.info("Orders query by startDate={} endDate={} is received", startDate, endDate);

        try {
            List<Order> orders = orderService.getOrdersByDate(startDate.atStartOfDay(), endDate.atStartOfDay());
            if (orders == null) {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(orders, HttpStatus.OK);

        } catch (Exception ex) {
            log.error("Exception is occurred during order fetching ", ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

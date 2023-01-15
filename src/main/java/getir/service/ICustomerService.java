package getir.service;

import getir.entity.Customer;
import getir.entity.Order;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ICustomerService {
    Customer createNewCustomer(Customer customer);
    List<Order> getCustomerAllOrders(String id, Pageable pageable);
}

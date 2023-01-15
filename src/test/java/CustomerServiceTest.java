import getir.entity.Customer;
import getir.entity.Order;
import getir.repository.ICustomerRepository;
import getir.service.CustomerService;
import getir.service.IOrderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class CustomerServiceTest {

    @InjectMocks
    private CustomerService customerService;

    @Mock
    private ICustomerRepository customerRepository;

    @Mock
    private IOrderService orderService;

    @Test
    void createNewCustomer() {

        String email = "test@gmail.com";
        String firstName = "name";
        String lastName = "lastname";
        String phone = "1111111111";
        String address = "adress";
        String password = "1";

      Customer customer = new Customer();
      customer.setEmail(email);
      customer.setFirstName(firstName);
      customer.setLastName(lastName);
      customer.setPhone(phone);
      customer.setPassword(password);
      customer.setAddress(address);

        when(customerRepository.save(any(Customer.class))).thenReturn(customer);
        when(customerRepository.findByEmail(any(String.class))).thenReturn(Optional.empty());

        Customer newCustomer = customerService.createNewCustomer(customer);

        assertNotNull(newCustomer);
        assertEquals(customer.getEmail(), newCustomer.getEmail());
        assertEquals(customer.getAddress(), newCustomer.getAddress());
        assertEquals(customer.getFirstName(), newCustomer.getFirstName());

    }

    @Test
    void getCustomerAllOrders() {

        String customerId = "c1001L";
        String status = "Created";
        LocalDateTime localDateTime = LocalDateTime.of(2021, 10, 11, 13, 30);
        PageRequest pageRequest = PageRequest.of(0, 20);

        Order order = new Order();
        order.setCustomerId(customerId);
        order.setStatus(status);
        order.setCreatedAt(localDateTime);

        List<Order> orders = Collections.singletonList(order);

        when(orderService.getOrdersByCustomerId(customerId, pageRequest)).thenReturn(orders);

        List<Order> customerAllOrders = customerService.getCustomerAllOrders(customerId, pageRequest);

        assertNotNull(customerAllOrders);
        assertEquals(orders.size(), customerAllOrders.size());
        assertEquals(orders.get(0).getStatus(), customerAllOrders.get(0).getStatus());
    }
}

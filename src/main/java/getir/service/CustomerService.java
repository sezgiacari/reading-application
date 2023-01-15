package getir.service;


import getir.entity.Customer;
import getir.entity.Order;
import getir.repository.ICustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomerService implements ICustomerService {

    private final ICustomerRepository customerRepository;
    private final IOrderService orderService;

    @Override
    public Customer createNewCustomer(Customer customer) {
        Optional<Customer> customerOptional = customerRepository.findByEmail(customer.getEmail());
        if (customerOptional.isPresent()) {
            log.error("Customer already exists with email={}", customer.getEmail());
            return null;
        }
        customerRepository.save(customer);
        log.info("New customer is created with email={}", customer.getEmail());
        return customer;
    }

    @Override
    public List<Order> getCustomerAllOrders(String customerId, Pageable pageable) {
        return  orderService.getOrdersByCustomerId(customerId, pageable);
    }
}
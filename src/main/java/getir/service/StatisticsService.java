package getir.service;

import getir.entity.CustomerMonthlyStatics;
import getir.entity.Order;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Month;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class StatisticsService implements IStatisticService {

    private final ICustomerService customerService;

    @Override
    public List<CustomerMonthlyStatics> getCustomerMonthlyStatics(String customerId) {
        //customer orders.
        List<Order> customerOrders = customerService.getCustomerAllOrders(customerId, Pageable.unpaged());

        // group customer orders by month
        Map<Month, List<Order>> result = customerOrders.stream()
                .collect(Collectors.groupingBy(order -> order.getCreatedAt().getMonth()));

        return result.entrySet().stream()
                .map(entry -> createCustomerMonthlyStatics(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    private CustomerMonthlyStatics createCustomerMonthlyStatics(Month month, List<Order> orderList) {
        int totalBookCount = 0;
        double totalPurchasedAmount = 0;

        for (Order order : orderList) {
            totalBookCount += order.getTotalBooks();
            totalPurchasedAmount += order.getTotalPrice();
        }

        return new CustomerMonthlyStatics(month.toString(), orderList.size(), totalBookCount, totalPurchasedAmount);
    }
}
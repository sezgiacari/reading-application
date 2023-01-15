package getir.service;

import getir.entity.CustomerMonthlyStatics;
import java.util.List;

public interface IStatisticService {
    List<CustomerMonthlyStatics> getCustomerMonthlyStatics(String customerId);
}
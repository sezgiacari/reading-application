package getir.controller;

import getir.entity.CustomerMonthlyStatics;
import getir.service.IStatisticService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/statistic")
@RequiredArgsConstructor
public class StatisticController {

    private final IStatisticService staticsService;

    @GetMapping("/monthly")
    public ResponseEntity<List<CustomerMonthlyStatics>> getCustomerMonthlyStatics(@RequestParam String customerId) {
        try {
            List<CustomerMonthlyStatics> customerMonthlyStatics = staticsService.getCustomerMonthlyStatics(customerId);
            return new ResponseEntity<>(customerMonthlyStatics, HttpStatus.OK);

        } catch (Exception ex) {
            log.error("Exception is occurred during customer monthly statics querying ", ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}

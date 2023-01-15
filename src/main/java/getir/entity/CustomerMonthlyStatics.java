package getir.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerMonthlyStatics {

    private String month;

    private Integer totalOrderCount;

    private Integer totalBookCount;

    private Double totalPurchasedAmount;
}

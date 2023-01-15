package getir.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
public class Order {
    @Id
    private String id;

    private String customerId;

    private List<Book> bookList;

    private Integer totalBooks;

    private Double totalPrice;

    private String status;

    private LocalDateTime createdAt = LocalDateTime.now();
}

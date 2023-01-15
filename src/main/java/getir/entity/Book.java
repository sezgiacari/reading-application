package getir.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
public class Book implements Serializable {

    @Id
    private String id;

    private String name;

    private String writer;

    private Double price;

    private Integer stock;

    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime updatedAt =LocalDateTime.now();
}

package getir.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Getter
@Setter
public class Customer {
    @Id
    private String id;

    private String email;

    private String password;


    private String firstName;

    private String lastName;
    private String phone;
    private String address;
    //private Boolean activeted;
    private LocalDateTime createdDate = LocalDateTime.now();
}

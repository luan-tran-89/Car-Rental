package rentalservice.domain;

import jakarta.persistence.*;
import lombok.Data;
import rentalservice.enums.FrequentRenterType;
import rentalservice.enums.UserRole;

@Data
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    private String username;
    private String password;  // this will be hashed for security purposes in the actual implementation
    private String firstname;
    private String lastname;
    private String email;
    private String phone;

    @Enumerated(EnumType.STRING)
    private UserRole role;
    @Enumerated(EnumType.STRING)
    private FrequentRenterType frequentRenterType;

}

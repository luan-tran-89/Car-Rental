package rentalservice.domain;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password; // Ensure you hash this before storing it for security purposes.

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;

    @Enumerated(EnumType.STRING)
    private FrequentRenterType frequentRenterType; // This can be null for users who aren't frequent renters.

    // Other attributes or methods as needed

    public enum UserRole {
        ADMIN, MANAGER, CUSTOMER, FREQUENT_RENTER
    }

    public enum FrequentRenterType {
        BRONZE, SILVER, GOLD
    }
}


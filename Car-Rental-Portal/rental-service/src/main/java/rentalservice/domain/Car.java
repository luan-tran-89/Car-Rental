package rentalservice.domain;

import lombok.Data;
import jakarta.persistence.*;

@Data
@Entity
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer carId;

    @Column(nullable = false)
    private String model;

    @Column(nullable = false)
    private String make;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CarStatus status;

    @Column(nullable = false)
    private Double baseCostPerDay;

    // Enums for car status
    public enum CarStatus {
        AVAILABLE,
        RESERVED,
        PICKED,
        UNDER_MAINTENANCE,
        DISABLED
    }

}


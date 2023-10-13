package rentalservice.domain;

import jakarta.persistence.*;
import lombok.Data;
import rentalservice.enums.CarStatus;

@Data
@Entity
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer carId;
    private String model;
    private String make;
    @Enumerated(EnumType.STRING)
    private CarStatus status;
    private Double fixedCost;  // Moved this before costPerDay
    private Double costPerDay;
    private String image;


}

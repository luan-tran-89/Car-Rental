package rentalservice.DTO;

import lombok.Data;
import rentalservice.domain.Car;
import rentalservice.domain.User;

import java.util.Date;

@Data
public class ReservationDTO {
    private Car car;
    private User user;
    private Date startDate;
    private Date endDate;

    // Getters, setters, etc...
}


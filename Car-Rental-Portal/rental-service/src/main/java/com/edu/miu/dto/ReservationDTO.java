package com.edu.miu.dto;

import lombok.Data;
import java.util.Date;

@Data
public class ReservationDTO {
    private Integer carId;
    private Integer userId;
    private Date startDate;
    private Date endDate;

    // Getters, setters, etc... (which will be generated by Lombok)
}

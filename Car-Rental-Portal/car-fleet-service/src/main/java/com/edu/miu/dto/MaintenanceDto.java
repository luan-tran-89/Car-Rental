package com.edu.miu.dto;

import com.edu.miu.enums.MaintenanceStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author gasieugru
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MaintenanceDto {

    private int id;

    private int carId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy")
    private Date startDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy")
    private Date endDate;

    private String description;

    private MaintenanceStatus status;
}

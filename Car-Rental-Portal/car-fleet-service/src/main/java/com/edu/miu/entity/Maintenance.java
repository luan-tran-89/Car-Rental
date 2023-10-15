package com.edu.miu.entity;

import com.edu.miu.enums.MaintenanceStatus;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author gasieugru
 */
@Entity
@Data
public class Maintenance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name="car_id", nullable=false)
    private Car car;

    @Column(name = "start_date")
    @CreationTimestamp
    private Date startDate;

    @Column(name = "end_date")
    @CreationTimestamp
    private Date endDate;

    private String description;

    @Enumerated(EnumType.STRING)
    private MaintenanceStatus status = MaintenanceStatus.IN_PROGRESS;
}

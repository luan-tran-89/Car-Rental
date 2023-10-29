package com.edu.miu.entity;

import com.edu.miu.enums.MaintenanceStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

/**
 * @author gasieugru
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
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
    @Column(name = "status", columnDefinition = "ENUM('IN_PROGRESS', 'FINISHED') default 'IN_PROGRESS'")
    private MaintenanceStatus status = MaintenanceStatus.IN_PROGRESS;
}

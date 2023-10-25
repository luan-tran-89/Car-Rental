package com.edu.miu.dto;

import com.edu.miu.enums.TimeReport;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportFilter {

    private int month;

    private int quarter;

    private int year;

    private TimeReport timeReport;
}

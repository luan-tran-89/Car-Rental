package com.edu.miu.controller;

import com.edu.miu.enums.ReportFormat;
import com.edu.miu.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author gasieugru
 */
@RestController
@RequestMapping("/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;


    @GetMapping("/car-reports/{carId}")
    public ResponseEntity getReport(@PathVariable("carId") int carId, @RequestParam(value = "format", defaultValue = "PDF") ReportFormat format) {
        byte[] reportContent = reportService.getCarReport(carId, format);

        ByteArrayResource resource = new ByteArrayResource(reportContent);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(resource.contentLength())
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        ContentDisposition.attachment()
                                .filename("car-report." + format)
                                .build().toString())
                .body(resource);
    }

}

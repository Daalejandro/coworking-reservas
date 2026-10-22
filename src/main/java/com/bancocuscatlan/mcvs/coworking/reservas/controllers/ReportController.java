package com.bancocuscatlan.mcvs.coworking.reservas.controllers;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bancocuscatlan.mcvs.coworking.reservas.dto.response.ReporteResponse;
import com.bancocuscatlan.mcvs.coworking.reservas.services.ReporteService;

@RestController
@RequestMapping("/api/reportes")
public class ReportController {


     private final ReporteService reportService;

    public ReportController(ReporteService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/ocupacion")
    public List<ReporteResponse> obtenerOcupacion(@RequestParam("desde")@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)LocalDateTime desde,

            @RequestParam("hasta")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime hasta) {

        return reportService.obtenerOcupacion(desde, hasta);
    }
}



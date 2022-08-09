package com.thelibrarian.core.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.lowagie.text.DocumentException;
import com.thelibrarian.data.entity.UsersEntity;
import com.thelibrarian.integration.ReservationPDF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.thelibrarian.data.entity.ReservationEntity;
import com.thelibrarian.data.repository.IBookDao;
import com.thelibrarian.data.service.ReservationServiceBBDD;


import lombok.RequiredArgsConstructor;

import javax.servlet.http.HttpServletResponse;


@RequiredArgsConstructor
@RestController
@RequestMapping("/reserve")
public class ReservationControllerBBDD {

    @Autowired
    ReservationServiceBBDD reservationService;

    @Autowired
    IBookDao bookDao;


    @GetMapping("/getAllReservation")
    public List<ReservationEntity> findAll() {

        return reservationService.findAll();
    }


    @PostMapping()
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<ReservationEntity> insert(@RequestBody ReservationEntity booking){

        return reservationService.createReserve(booking);
    }

    @PutMapping("/updateReserve/{id}")
    public ResponseEntity<ReservationEntity> Update(@RequestBody ReservationEntity reservation, @PathVariable Integer id) {
        ReservationEntity reservation1 = reservationService.Update(reservation, id);

        return ResponseEntity.ok().body(reservation1);
    }

    @GetMapping("/reserve/export/pdf")
    public void exportToPDF(HttpServletResponse response) throws DocumentException, IOException {
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);

        List<ReservationEntity> list = reservationService.findAll();

        ReservationPDF exporter = new ReservationPDF(list);
        exporter.exportUser(response);

    }
    
}

package com.thelibrarian.core.controller;

import com.lowagie.text.DocumentException;
import com.thelibrarian.BookPDF;
import com.thelibrarian.data.entity.BookEntity;
import com.thelibrarian.data.service.BookServiceBBDD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

    @RestController
    public class BookControllerBBDD {

        @Autowired
        BookServiceBBDD bookService;

        @GetMapping("/getAllBooks")
        public List<BookEntity> findAll() {

            return bookService.findAll();
        }


        @PostMapping("/createBook")
        @ResponseStatus(code = HttpStatus.CREATED)
        public BookEntity insert(@RequestBody BookEntity book) {
           return bookService.save(book);
        }

        @DeleteMapping("/deleteBook/{id}")
        @ResponseStatus(code = HttpStatus.NO_CONTENT)
        public void delete(@PathVariable Integer id) {
            bookService.delete(id);
        }

        @PutMapping("/updateBook/{id}")
        public ResponseEntity<BookEntity> Update(@RequestBody BookEntity book, @PathVariable Integer id) {
            BookEntity book1 = bookService.Update(book, id);

            return ResponseEntity.ok().body(book1);
        }

        @GetMapping("/getByIdBook")
        public BookEntity findById(Integer id) {

            return bookService.findById(id);
        }

        @GetMapping("/getByIsbn")
        public BookEntity findByIsbn(String isbn) {

            return bookService.findByIsbn(isbn);

        }

        @GetMapping("/book/export/pdf")
        public void exportToPDF(HttpServletResponse response) throws DocumentException, IOException {
            response.setContentType("application/pdf");
            DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
            String currentDateTime = dateFormatter.format(new Date());

            String headerKey = "Content-Disposition";
            String headerValue = "attachment; filename=users_" + currentDateTime + ".pdf";
            response.setHeader(headerKey, headerValue);

            List<BookEntity> list = bookService.findAll();

            BookPDF exporter = new BookPDF(list);
            exporter.export(response);

        }
    }


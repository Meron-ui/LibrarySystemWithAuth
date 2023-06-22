package com.example.librarysystem.Controller;

import com.example.librarysystem.Service.CheckoutService;
import com.example.librarysystem.dto.Checkout;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/checkin")
public class CheckinController {

    private final CheckoutService checkoutService;

    public CheckinController(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }


    @PreAuthorize("hasRole('STAFF') or hasRole('ADMIN')")
    @PostMapping()
    public ResponseEntity<Void> checkinBook(@Validated @RequestBody Checkout checkinDTO) {
        boolean isCheckinSuccessful = checkoutService.checkinBook(checkinDTO);
        if (isCheckinSuccessful) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}

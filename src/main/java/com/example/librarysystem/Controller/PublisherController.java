package com.example.librarysystem.Controller;

import com.example.librarysystem.Service.PublisherService;
import com.example.librarysystem.dto.Publisher;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/publishers")
public class PublisherController {

    private final PublisherService publisherService;

    public PublisherController(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    @PreAuthorize("hasRole('STAFF') or hasRole('ADMIN')")
    @GetMapping
    public List<Publisher> getAllPublishers() {
        return publisherService.getAllPublishers();
    }

    @PreAuthorize("hasRole('STAFF') or hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Publisher> addPublisher(@Validated @RequestBody Publisher publisherDTO) {
        Publisher publisher = publisherService.addPublisher(publisherDTO);
        return ResponseEntity.ok(publisher);
    }

    @PreAuthorize("hasRole('STAFF') or hasRole('ADMIN')")
    @GetMapping("/{publisherId}")
    public ResponseEntity<Publisher> getPublisherById(@PathVariable Long publisherId) {
        Publisher publisher = publisherService.getPublisherById(publisherId);
        if (publisher != null) {
            return ResponseEntity.ok(publisher);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PreAuthorize("hasRole('STAFF') or hasRole('ADMIN')")
    @PutMapping("/{publisherId}")
    public ResponseEntity<Publisher> updatePublisher(@PathVariable Long publisherId, @Validated @RequestBody Publisher publisherDTO) {
        Publisher updatedPublisher = publisherService.updatePublisher(publisherId, publisherDTO);
        if (updatedPublisher != null) {
            return ResponseEntity.ok(updatedPublisher);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PreAuthorize("hasRole('STAFF') or hasRole('ADMIN')")
    @DeleteMapping("/{publisherId}")
    public ResponseEntity<Void> deletePublisher(@PathVariable Long publisherId) {
        boolean isDeleted = publisherService.deletePublisher(publisherId);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

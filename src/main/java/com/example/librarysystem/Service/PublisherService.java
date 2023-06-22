package com.example.librarysystem.Service;

import com.example.librarysystem.dto.Publisher;
import org.springframework.stereotype.Service;

import java.util.List;


public interface PublisherService {

    List<Publisher> getAllPublishers();

    Publisher addPublisher(Publisher publisherDTO);

    Publisher getPublisherById(Long publisherId);

    Publisher updatePublisher(Long publisherId, Publisher publisherDTO);

    boolean deletePublisher(Long publisherId);
}

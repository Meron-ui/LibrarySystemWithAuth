package com.example.librarysystem.Service;

import com.example.librarysystem.Repository.PublisherRepository;
import com.example.librarysystem.dto.Book;
import com.example.librarysystem.dto.Publisher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PublisherServiceImpl implements PublisherService {


    private final PublisherRepository publisherRepository;

    public PublisherServiceImpl(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    @Override
    public List<Publisher> getAllPublishers() {
        return publisherRepository.findAll();
    }

    @Override
    public Publisher addPublisher(Publisher publisherDTO) {
        Publisher publisher = new Publisher();
        publisher.setAddress(publisherDTO.getAddress());
        publisher.setName(publisherDTO.getName());
        publisher.setPhoneNumber(publisherDTO.getPhoneNumber());
        return publisherRepository.save(publisher);
    }

    @Override
    public Publisher getPublisherById(Long publisherId) {
        return publisherRepository.findById(publisherId).orElse(null);
    }

    @Override
    public Publisher updatePublisher(Long publisherId, Publisher publisherDTO) {
        Optional<Publisher> entity = publisherRepository.findById(publisherId);
        if(entity.isPresent()){
            Publisher publisher = entity.get();
            publisher.setPhoneNumber(publisherDTO.getPhoneNumber());
            publisher.setAddress(publisherDTO.getAddress());
            publisher.setName(publisherDTO.getName());
            publisherRepository.save(publisher);
        }
        return null;
    }

    @Override
    public boolean deletePublisher(Long publisherId) {
         Publisher existingPublisher = getPublisherById(publisherId);
        if (existingPublisher != null) {
            publisherRepository.delete(existingPublisher);
            return true;
        }
        return false;
    }
}

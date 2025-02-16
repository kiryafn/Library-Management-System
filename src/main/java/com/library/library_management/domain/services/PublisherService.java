package com.library.library_management.domain.services;

import com.library.library_management.data.entities.Publisher;
import com.library.library_management.data.repository.PublisherRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PublisherService {

    private final PublisherRepository publisherRepository;

    public PublisherService(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    public void insert(Publisher publisher) {
        validatePublisher(publisher);
        publisherRepository.save(publisher);
    }

    public void update(Publisher publisher) {
        validatePublisher(publisher);
        publisherRepository.save(publisher);
    }

    public void delete(Long id) {
        Publisher toDelete = publisherRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Publishes with id " + id + " not found"));
        if (toDelete.getBooks() != null && !toDelete.getBooks().isEmpty()) {
            throw new IllegalStateException("Невозможно удалить издателя, у которого есть связанные книги.");
        }
        publisherRepository.delete(id);
    }

    public List<Publisher> getAll() {
        return publisherDAO.getAll();
    }

    public void validatePublisher(Publisher publisher){
        if (publisher.getName() == null || publisher.getName().isEmpty()) {
            throw new IllegalArgumentException("Publisher name can`t be empty.");
        }
    }
}
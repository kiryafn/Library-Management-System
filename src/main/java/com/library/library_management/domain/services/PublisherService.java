package com.library.library_management.domain.services;

import com.library.library_management.data.dao.PublisherDAO;
import com.library.library_management.data.entities.Publisher;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class PublisherService {
    private final PublisherDAO publisherDAO;

    public PublisherService(PublisherDAO publisherDAO) {
        this.publisherDAO = publisherDAO;
    }

    public void addPublisher(Publisher publisher) {
        publisherDAO.insert(publisher);
    }

    public void deletePublisher(int id) {
        publisherDAO.delete(id);
    }

    public List<Publisher> getAllPublishers() {
        return publisherDAO.getAll();
    }
}

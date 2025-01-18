package com.library.library_management.domain.services;

import com.library.library_management.data.dao.BookDAO;
import com.library.library_management.data.dto.BookWithAvailableCopiesDTO;
import com.library.library_management.data.entities.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final BookDAO bookDAO;

    @Autowired
    public BookService(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }

    public List<BookWithAvailableCopiesDTO> getBooksWithAvailableCopies() {
        List<Object[]> results = bookDAO.findBooksWithAvailableCopies();
        return results.stream()
                .map(result -> new BookWithAvailableCopiesDTO((Book) result[0], ((Long) result[1]).intValue()))
                .toList();
    }
}
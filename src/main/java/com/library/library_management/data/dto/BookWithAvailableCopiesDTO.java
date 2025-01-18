package com.library.library_management.data.dto;

import com.library.library_management.data.entities.Book;

public class BookWithAvailableCopiesDTO {

    private Book book;
    private int availableCopies;

    public BookWithAvailableCopiesDTO(Book book, int availableCopies) {
        this.book = book;
        this.availableCopies = availableCopies;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public int getAvailableCopies() {
        return availableCopies;
    }

    public void setAvailableCopies(int availableCopies) {
        this.availableCopies = availableCopies;
    }
}
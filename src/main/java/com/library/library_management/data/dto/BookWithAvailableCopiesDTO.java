package com.library.library_management.data.dto;

import com.library.library_management.data.entities.Book;

/**
 * Data Transfer Object (DTO) representing a book along with the count of its available copies.
 *
 * <p>The {@code BookWithAvailableCopiesDTO} class is used to encapsulate data related to a specific
 * {@link Book} entity and the number of copies of that book currently available for borrowing or use.
 * This class is primarily intended for use in service or controller layers where a combination
 * of book details and availability information needs to be transferred.</p>
 */
public class BookWithAvailableCopiesDTO {

    /**
     * The {@link Book} entity representing the details of the book.
     */
    private Book book;

    /**
     * The number of available copies of the book.
     */
    private int availableCopies;

    /**
     * Constructs a new {@code BookWithAvailableCopiesDTO} with the specified book and available copies count.
     *
     * @param book the {@link Book} entity representing the details of the book
     * @param availableCopies the number of copies of the book currently available
     */
    public BookWithAvailableCopiesDTO(Book book, int availableCopies) {
        this.book = book;
        this.availableCopies = availableCopies;
    }

    /**
     * Gets the {@link Book} entity associated with this DTO.
     *
     * @return the {@link Book} object representing the details of the book
     */
    public Book getBook() {
        return book;
    }

    /**
     * Sets the {@link Book} entity associated with this DTO.
     *
     * @param book the new {@link Book} entity to associate with this DTO
     */
    public void setBook(Book book) {
        this.book = book;
    }

    /**
     * Gets the number of available copies of the book.
     *
     * @return the number of available copies
     */
    public int getAvailableCopies() {
        return availableCopies;
    }

    /**
     * Sets the number of available copies of the book.
     *
     * @param availableCopies the new number of available copies
     */
    public void setAvailableCopies(int availableCopies) {
        this.availableCopies = availableCopies;
    }
}
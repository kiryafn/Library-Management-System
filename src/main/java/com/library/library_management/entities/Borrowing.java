package com.library.library_management.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

@Entity
@Table(name = "BORROWING")
public class Borrowing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "copy_id")
    private Copy copy;

    @NotNull
    private LocalDate borrowDate;

    private LocalDate returnDate;

    public Borrowing() {}

    public Borrowing(User user, Copy copy, LocalDate borrowDate, LocalDate returnDate) {
        this.user = user;
        this.copy = copy;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
    }

    /**
     * Gets the unique identifier for this borrowing record.
     *
     * @return the ID of the borrowing record
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the unique identifier for this borrowing record.
     *
     * @param id the new ID of the borrowing record
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the user who borrowed the copy.
     *
     * @return the {@link User} who borrowed the copy
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets the user who borrowed the copy.
     *
     * @param user the {@link User} who borrowed the copy
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Gets the copy of the book that was borrowed.
     *
     * @return the {@link Copy} of the book
     */
    public Copy getCopy() {
        return copy;
    }

    /**
     * Sets the copy of the book that was borrowed.
     *
     * @param copy the {@link Copy} of the book
     */
    public void setCopy(Copy copy) {
        this.copy = copy;
    }

    /**
     * Gets the date when the borrowing occurred.
     *
     * @return the borrowing date
     */
    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    /**
     * Sets the date when the borrowing occurred.
     *
     * @param borrowDate the new borrowing date
     */
    public void setBorrowDate(LocalDate borrowDate) {
        this.borrowDate = borrowDate;
    }

    /**
     * Gets the date when the borrowed copy was returned.
     *
     * @return the return date, or {@code null} if the copy has not been returned
     */
    public LocalDate getReturnDate() {
        return returnDate;
    }

    /**
     * Sets the date when the borrowed copy was returned.
     *
     * @param returnDate the new return date
     */
    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }
}
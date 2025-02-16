package com.library.library_management.data.entities;

import jakarta.persistence.*;
import java.time.LocalDate;

/**
 * Entity class representing a borrowing record in the library management system.
 *
 * <p>The {@code Borrowing} entity logs the borrowing of a specific {@link Copy} by a {@link User},
 * along with the borrowing and optional return dates. It is mapped to the {@code BORROWING} table
 * in the database using JPA annotations.</p>
 *
 * <p>This class defines relationships with the {@link User} and {@link Copy} entities,
 * ensuring that each borrowing event is associated with a borrower and a specific copy of a book.</p>
 */
@Entity
@Table(name = "BORROWING")
public class Borrowing {

    /**
     * The unique identifier for this borrowing record.
     *
     * <p>This field is the primary key of the {@code BORROWING} table and is
     * auto-generated using the {@link GenerationType#IDENTITY} strategy.</p>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The {@link User} who borrowed the {@link Copy}.
     *
     * <p>This field establishes a many-to-one relationship with the {@link User} entity.
     * It is mandatory and mapped using a foreign key {@code person_id} in the database table.</p>
     */
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * The {@link Copy} of the book that is being borrowed.
     *
     * <p>This field establishes a many-to-one relationship with the {@link Copy} entity.
     * It is mandatory and mapped using a foreign key {@code copy_id} in the database table.</p>
     */
    @ManyToOne
    @JoinColumn(name = "copy_id", nullable = false)
    private Copy copy;

    /**
     * The date on which the borrowing occurred.
     *
     * <p>This field is mandatory and is mapped to a non-null column in the database.</p>
     */
    @Column(nullable = false)
    private LocalDate borrowDate;

    /**
     * The date on which the borrowed copy was returned.
     *
     * <p>This field is optional and is mapped to a nullable column in the database.
     * If the copy has not yet been returned, this field will remain {@code null}.</p>
     */
    @Column
    private LocalDate returnDate;

    /**
     * Default no-argument constructor for JPA.
     */
    public Borrowing() {}

    /**
     * Constructs a new {@link Borrowing} instance with the specified user, copy,
     * borrowing date, and return date.
     *
     * @param user the {@link User} who borrowed the copy
     * @param copy the {@link Copy} of the book that was borrowed
     * @param borrowDate the date the borrowing occurred
     * @param returnDate the date the copy was returned (optional, can be {@code null})
     */
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
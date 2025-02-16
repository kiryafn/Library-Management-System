package com.library.library_management.data.entities;

import jakarta.persistence.*;

/**
 * Entity class representing a specific copy of a book in the library management system.
 *
 * <p>The {@code Copy} entity tracks details about a physical copy of a {@link Book}, including
 * its unique identifier, book association, copy number, and current status. It is mapped to
 * the {@code COPY} table in the database via JPA annotations.</p>
 *
 * <p>The class also provides utility methods to manage and query the status of a copy, such as
 * marking it as borrowed, reserved, or available.</p>
 */
@Entity
@Table(name = "COPY")
public class Copy {

    /**
     * The unique identifier for a copy.
     *
     * <p>This field is the primary key of the {@code COPY} table and is auto-generated using
     * the {@link GenerationType#IDENTITY} strategy.</p>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The {@link Book} that this copy belongs to.
     *
     * <p>This field forms a many-to-one relationship with the {@link Book} entity.
     * It is mandatory and is represented in the database by the {@code book_id} foreign key column.</p>
     */
    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    /**
     * A unique number identifying this specific copy of the book.
     *
     * <p>This field represents the individual identifier of a copy within the scope of its associated book
     * (e.g., "Copy 1", "Copy 2", ...). It is mandatory and mapped to a non-null column in the database.</p>
     */
    @Column(nullable = false)
    private Integer copyNumber;

    /**
     * The current status of the copy.
     *
     * <p>This field indicates whether the copy is available, borrowed, reserved, or lost.
     * It is mandatory and represented as a string in the database.</p>
     */
    @Column(nullable = false)
    private String status;

    /**
     * Default no-argument constructor for JPA.
     */
    public Copy() {}

    /**
     * Constructs a new {@link Copy} with the specified book, copy number, and status.
     *
     * @param book the {@link Book} that this copy belongs to
     * @param copyNumber the unique number identifying this specific copy within the book
     * @param status the initial status of the copy (e.g., available, borrowed, reserved, or lost)
     */
    public Copy(Book book, Integer copyNumber, String status) {
        this.book = book;
        this.copyNumber = copyNumber;
        this.status = status;
    }

    /**
     * Gets the unique ID of the copy.
     *
     * @return the unique ID of the copy
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the ID of the copy.
     *
     * @param id the new ID of the copy
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the {@link Book} associated with the copy.
     *
     * @return the {@link Book} associated with this copy
     */
    public Book getBook() {
        return book;
    }

    /**
     * Sets the associated {@link Book} for the copy.
     *
     * @param book the new associated {@link Book}
     */
    public void setBook(Book book) {
        this.book = book;
    }

    /**
     * Gets the copy number (unique within the scope of the associated book).
     *
     * @return the copy number
     */
    public Integer getCopyNumber() {
        return copyNumber;
    }

    /**
     * Sets the copy number (unique within the scope of the associated book).
     *
     * @param copyNumber the new copy number
     */
    public void setCopyNumber(Integer copyNumber) {
        this.copyNumber = copyNumber;
    }

    /**
     * Gets the current status of the copy.
     *
     * <p>The status can represent values such as available, borrowed, reserved, or lost.</p>
     *
     * @return the current status of the copy
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the current status of the copy.
     *
     * @param status the new status (e.g., available, borrowed, reserved, or lost)
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Checks if the copy is currently available.
     *
     * @return {@code true} if the copy is available, {@code false} otherwise
     */
    public boolean isAvailable() {
        return this.status.equals(CopyStatus.Available.getName());
    }

    /**
     * Marks this copy as available.
     *
     * <p>Updates the status of the copy to "available."</p>
     */
    public void markAsAvailable() {
        this.status = CopyStatus.Available.getName();
    }

    /**
     * Marks this copy as borrowed.
     *
     * <p>Updates the status of the copy to "borrowed."</p>
     */
    public void markAsBorrowed() {
        this.status = CopyStatus.Borrowed.getName();
    }

    /**
     * Marks this copy as reserved.
     *
     * <p>Updates the status of the copy to "reserved."</p>
     */
    public void markAsReserved() {
        this.status = CopyStatus.Reserved.getName();
    }

    /**
     * Marks this copy as lost.
     *
     * <p>Updates the status of the copy to "lost."</p>
     */
    public void markAsLost() {
        this.status = CopyStatus.Lost.getName();
    }
}
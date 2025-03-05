package com.library.library_management.entities;

import jakarta.persistence.*;

import java.util.List;

/**
 * Entity class representing a book in the library management system.
 *
 * <p>The {@code Book} entity contains details about a book, such as its title, author,
 * publisher, publication year, and ISBN. It is mapped to the {@code BOOK} table in the database
 * and establishes relationships with other entities such as {@link Publisher} and {@link Copy}.
 * Each book can have multiple physical copies in the library.</p>
 *
 * <p>This class utilizes JPA annotations for ORM (Object-Relational Mapping) and allows for the
 * management of books via persistence in the database.</p>
 */
@Entity
@Table(name = "BOOK")
public class Book {

    /**
     * The unique identifier for the book.
     *
     * <p>This field is the primary key of the {@code BOOK} table in the database and is
     * auto-generated using the {@link GenerationType#IDENTITY} strategy.</p>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The title of the book.
     *
     * <p>This field is mandatory and is mapped to a non-null column in the database.</p>
     */
    @Column(nullable = false)
    private String title;

    /**
     * The author of the book.
     *
     * <p>This field is mandatory and is mapped to a non-null column in the database.</p>
     */
    @Column(nullable = false)
    private String author;

    /**
     * The publisher of the book.
     *
     * <p>This field establishes a many-to-one relationship with the {@link Publisher} entity
     * using a foreign key column {@code publisher_id}. The relationship is mandatory.</p>
     */
    @ManyToOne
    @JoinColumn(name = "publisher_id", nullable = false)
    private Publisher publisher;

    /**
     * The year the book was published.
     *
     * <p>This field is mandatory and is mapped to a non-null column in the database.</p>
     */
    @Column(nullable = false)
    private Integer publicationYear;

    /**
     * The International Standard Book Number (ISBN) of the book.
     *
     * <p>This field is mandatory, unique, and is mapped to a non-null, unique column in the database.</p>
     */
    @Column(nullable = false, unique = true)
    private String isbn;

    /**
     * The list of physical copies associated with the book.
     *
     * <p>This field establishes a one-to-many relationship with the {@link Copy} entity,
     * where the {@link Copy} entities are mapped by the {@code book} field.</p>
     */
    @OneToMany(mappedBy = "book")
    private List<Copy> copies;

    /**
     * Default no-argument constructor for JPA.
     */
    public Book() {}

    /**
     * Constructs a new {@link Book} instance with the specified title, author, publisher,
     * publication year, and ISBN.
     *
     * @param title the title of the book
     * @param author the author of the book
     * @param publisher the publisher of the book
     * @param publicationYear the year the book was published
     * @param isbn the ISBN of the book
     */
    public Book(String title, String author, Publisher publisher, Integer publicationYear, String isbn) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.publicationYear = publicationYear;
        this.isbn = isbn;
    }

    /**
     * Gets the ID of the book.
     *
     * @return the ID of the book
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the ID of the book.
     *
     * @param id the new ID of the book
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the title of the book.
     *
     * @return the title of the book
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the book.
     *
     * @param title the new title of the book
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the author of the book.
     *
     * @return the author of the book
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Sets the author of the book.
     *
     * @param author the new author of the book
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * Gets the publisher of the book.
     *
     * @return the publisher of the book
     */
    public Publisher getPublisher() {
        return publisher;
    }

    /**
     * Sets the publisher of the book.
     *
     * @param publisher the new publisher of the book
     */
    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    /**
     * Gets the publication year of the book.
     *
     * @return the publication year of the book
     */
    public Integer getPublicationYear() {
        return publicationYear;
    }

    /**
     * Sets the publication year of the book.
     *
     * @param publicationYear the new publication year of the book
     */
    public void setPublicationYear(Integer publicationYear) {
        this.publicationYear = publicationYear;
    }

    /**
     * Gets the ISBN of the book.
     *
     * @return the ISBN of the book
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     * Sets the ISBN of the book.
     *
     * @param isbn the new ISBN of the book
     */
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    /**
     * Gets the list of copies of the book.
     *
     * @return the list of copies
     */
    public List<Copy> getCopies() {
        return copies;
    }

    /**
     * Sets the list of copies of the book.
     *
     * @param copies the new list of copies
     */
    public void setCopies(List<Copy> copies) {
        this.copies = copies;
    }


    /**
     * Gets the number of physical copies of the book.
     *
     * @return the number of physical copies of the book
     */
    public Integer getNumberOfCopies() {
        return copies.size();
    }
}
package com.library.library_management.entities;

import jakarta.persistence.*;
import java.util.Set;

/**
 * Entity class representing a publisher in the library management system.
 *
 * <p>The {@code PublisherRepository} entity stores information about publishing companies or individuals
 * responsible for publishing books. This class is mapped to the {@code PUBLISHER} table in the database
 * using JPA annotations. It maintains a one-to-many relationship with the {@link Book} entity, where
 * a publisher can have multiple books.</p>
 */
@Entity
@Table(name = "PUBLISHER")
public class Publisher {

    /**
     * The unique identifier of the publisher.
     *
     * <p>This field is the primary key of the {@code PUBLISHER} table and is auto-generated using
     * the {@link GenerationType#IDENTITY} strategy.</p>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    /**
     * The name of the publisher.
     *
     * <p>This field is mandatory and non-null, representing the name of the publishing company or individual.</p>
     */
    @Column(name = "NAME", nullable = false)
    private String name;

    /**
     * The address of the publisher.
     *
     * <p>This field is optional and stores the physical or mailing address of the publisher.</p>
     */
    @Column(name = "ADDRESS")
    private String address;

    /**
     * The phone number of the publisher.
     *
     * <p>This field is optional and stores the contact phone number of the publisher. It is restricted
     * to a maximum length of 20 characters in the database.</p>
     */
    @Column(name = "PHONENUMBER", length = 20)
    private String phonenumber;

    /**
     * The collection of books published by this publisher.
     *
     * <p>This field defines a one-to-many relationship with the {@link Book} entity. Each book has
     * a reference to its publisher, and this collection represents all books associated with the publisher.</p>
     */
    @OneToMany(mappedBy = "publisher")
    private Set<Book> books;

    /**
     * Default no-argument constructor for JPA.
     */
    public Publisher() {}

    public Publisher(String name, String address, String phonenumber) {
        this.name = name;
        this.address = address;
        this.phonenumber = phonenumber;
    }

    /**
     * Gets the unique identifier of the publisher.
     *
     * @return the ID of the publisher
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the publisher.
     *
     * @param id the new ID of the publisher
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the name of the publisher.
     *
     * @return the name of the publisher
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the publisher.
     *
     * @param name the new name of the publisher
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the address of the publisher.
     *
     * @return the address of the publisher, or {@code null} if not set
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the address of the publisher.
     *
     * @param address the new address of the publisher
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Gets the phone number of the publisher.
     *
     * @return the phone number of the publisher, or {@code null} if not set
     */
    public String getPhonenumber() {
        return phonenumber;
    }

    /**
     * Sets the phone number of the publisher.
     *
     * @param phonenumber the new phone number of the publisher
     */
    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    /**
     * Gets the collection of books published by this publisher.
     *
     * @return a set containing {@link Book} entities associated with this publisher
     */
    public Set<Book> getBooks() {
        return books;
    }

    /**
     * Sets the collection of books published by this publisher.
     *
     * @param books the new collection of {@link Book} entities associated with this publisher
     */
    public void setBooks(Set<Book> books) {
        this.books = books;
    }
}
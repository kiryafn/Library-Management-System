package com.library.library_management.data.entities;

import jakarta.persistence.*;

import java.util.List;

/**
 * Entity class representing a person in the library management system.
 *
 * <p>The {@code User} entity stores user-specific details, which may represent
 * a library user or a librarian. This class is mapped to the {@code PERSON} table
 * in the database using JPA annotations, and it maintains relationships with
 * {@link Borrowing} and {@link Librarian} entities.</p>
 *
 * <p>The class provides attributes for common user details such as name, email,
 * phone number, and address. Additionally, it tracks the list of borrowings
 * associated with the person and whether the person has a librarian role.</p>
 */
@Entity
@Table(name = "Users")
public class User {

    /**
     * The unique identifier for the person.
     *
     * <p>This field is the primary key of the {@code PERSON} table and is auto-generated
     * using the {@link GenerationType#IDENTITY} strategy.</p>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The name of the person.
     *
     * <p>This field is non-null and stores the full name of the person.</p>
     */
    @Column(nullable = false)
    private String name;

    /**
     * The unique email address of the person.
     *
     * <p>This field is non-null and enforces uniqueness in the database. It represents
     * the person's email, which may be used for communication or identification.</p>
     */
    @Column(nullable = false, unique = true)
    private String email;

    /**
     * The phone number of the person.
     *
     * <p>This field is non-null and stores the contact phone number of the person.</p>
     */
    @Column(nullable = false)
    private String phoneNumber;

    /**
     * The address of the person.
     *
     * <p>This field is optional and stores the person's residential or mailing address.</p>
     */
    @Column
    private String address;

    /**
     * The list of borrowings associated with the person.
     *
     * <p>This field establishes a one-to-many relationship with the {@link Borrowing} entity,
     * representing all borrow transactions linked to the person.</p>
     */
    @OneToMany(mappedBy = "user")
    private List<Borrowing> borrowings;

    /**
     * The librarian information associated with this person, if applicable.
     *
     * <p>This field defines a one-to-one relationship with the {@link Librarian} entity.
     * If the person is a librarian, this attribute will store the corresponding librarian details.</p>
     */
    @OneToOne(mappedBy = "user")
    private Librarian librarian;

    /**
     * Default no-argument constructor for JPA.
     */
    public User() {}

    /**
     * Constructs a new {@link User} with the specified name, email, phone number, and address.
     *
     * @param name the name of the person
     * @param email the unique email address of the person
     * @param phoneNumber the phone number of the person
     * @param address the address of the person
     */
    public User(String name, String email, String phoneNumber, String address) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    /**
     * Gets the unique identifier of the person.
     *
     * @return the ID of the person
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the person.
     *
     * @param id the new ID of the person
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the name of the person.
     *
     * @return the name of the person
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the person.
     *
     * @param name the new name of the person
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the unique email address of the person.
     *
     * @return the email address of the person
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the unique email address of the person.
     *
     * @param email the new email address of the person
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the phone number of the person.
     *
     * @return the phone number of the person
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets the phone number of the person.
     *
     * @param phoneNumber the new phone number of the person
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Gets the address of the person.
     *
     * @return the address of the person, or {@code null} if not set
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the address of the person.
     *
     * @param address the new address of the person
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Gets the list of borrowings associated with the person.
     *
     * @return the list of {@link Borrowing} instances linked to this person
     */
    public List<Borrowing> getBorrowings() {
        return borrowings;
    }

    /**
     * Sets the list of borrowings associated with the person.
     *
     * @param borrowings the new list of {@link Borrowing} instances linked to this person
     */
    public void setBorrowings(List<Borrowing> borrowings) {
        this.borrowings = borrowings;
    }

    /**
     * Gets the librarian entity associated with the person, if applicable.
     *
     * @return the {@link Librarian} entity linked to this person, or {@code null} if the person is not a librarian
     */
    public Librarian getLibrarian() {
        return librarian;
    }

    /**
     * Sets the librarian entity associated with the person.
     *
     * @param librarian the new {@link Librarian} entity to be linked to this person
     */
    public void setLibrarian(Librarian librarian) {
        this.librarian = librarian;
    }

}
package com.library.library_management.data.entities;

import jakarta.persistence.*;
import java.time.LocalDate;

/**
 * Entity class representing a librarian in the library management system.
 *
 * <p>The {@code Librarian} entity captures details about a librarian, including their personal
 * information, employment date, and position within the library. This class is mapped to the
 * {@code LIBRARIAN} table in the database using JPA annotations and maintains a relationship
 * with the {@link User} class.</p>
 *
 * <p>Each librarian is uniquely associated with a {@link User} entity to store personal
 * details like name, contact information, and other general data.</p>
 */
@Entity
public class Librarian {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The {@link User} entity associated with this librarian.
     *
     * <p>This field defines a one-to-one relationship with the {@link User} entity. It is
     * mandatory and enforces uniqueness in the database using the {@code person_id} foreign
     * key column.</p>
     */
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    /**
     * The date the librarian was employed.
     *
     * <p>This field is mandatory and captures the date the librarian joined the library staff.</p>
     */
    @Column(nullable = false)
    private LocalDate employmentDate;

    /**
     * The position or role of the librarian within the library.
     *
     * <p>This field is mandatory and stores the job title, such as "Senior Librarian" or
     * "Library Assistant".</p>
     */
    @Column(nullable = false)
    private String position;

    /**
     * Default no-argument constructor for JPA.
     */
    public Librarian() {}

    /**
     * Constructs a new {@link Librarian} with the specified user, employment date, and position.
     *
     * @param user the {@link User} entity associated with the librarian
     * @param employmentDate the date the librarian was employed
     * @param position the position or role of the librarian in the library
     */
    public Librarian(User user, LocalDate employmentDate, String position) {
        this.user = user;
        this.employmentDate = employmentDate;
        this.position = position;
    }

    /**
     * Gets the unique identifier for the librarian.
     *
     * @return the ID of the librarian
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the unique identifier for the librarian.
     *
     * @param id the new ID of the librarian
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the {@link User} entity associated with the librarian.
     *
     * @return the {@link User} entity
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets the {@link User} entity for the librarian.
     *
     * @param user the new {@link User} entity
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Gets the employment date of the librarian.
     *
     * @return the employment date
     */
    public LocalDate getEmploymentDate() {
        return employmentDate;
    }

    /**
     * Sets the employment date of the librarian.
     *
     * @param employmentDate the new employment date
     */
    public void setEmploymentDate(LocalDate employmentDate) {
        this.employmentDate = employmentDate;
    }

    /**
     * Gets the librarian's position or role within the library.
     *
     * @return the position of the librarian
     */
    public String getPosition() {
        return position;
    }

    /**
     * Sets the position or role of the librarian within the library.
     *
     * @param position the new position of the librarian
     */
    public void setPosition(String position) {
        this.position = position;
    }

}
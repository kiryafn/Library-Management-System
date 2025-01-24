package com.library.library_management.data.entities;

import jakarta.persistence.*;
import java.time.LocalDate;

/**
 * Entity class representing a librarian in the library management system.
 *
 * <p>The {@code Librarian} entity captures details about a librarian, including their personal
 * information, employment date, and position within the library. This class is mapped to the
 * {@code LIBRARIAN} table in the database using JPA annotations and maintains a relationship
 * with the {@link Person} class.</p>
 *
 * <p>Each librarian is uniquely associated with a {@link Person} entity to store personal
 * details like name, contact information, and other general data.</p>
 */
@Entity
@Table(name = "LIBRARIAN")
public class Librarian {

    /**
     * The unique identifier for the librarian.
     *
     * <p>This field is the primary key of the {@code LIBRARIAN} table and is
     * auto-generated using the {@link GenerationType#IDENTITY} strategy.</p>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * The {@link Person} entity associated with this librarian.
     *
     * <p>This field defines a one-to-one relationship with the {@link Person} entity. It is
     * mandatory and enforces uniqueness in the database using the {@code person_id} foreign
     * key column.</p>
     */
    @OneToOne
    @JoinColumn(name = "person_id", nullable = false, unique = true)
    private Person person;

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
     * Constructs a new {@link Librarian} with the specified person, employment date, and position.
     *
     * @param person the {@link Person} entity associated with the librarian
     * @param employmentDate the date the librarian was employed
     * @param position the position or role of the librarian in the library
     */
    public Librarian(Person person, LocalDate employmentDate, String position) {
        this.person = person;
        this.employmentDate = employmentDate;
        this.position = position;
    }

    /**
     * Gets the unique identifier for the librarian.
     *
     * @return the ID of the librarian
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets the unique identifier for the librarian.
     *
     * @param id the new ID of the librarian
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Gets the {@link Person} entity associated with the librarian.
     *
     * @return the {@link Person} entity
     */
    public Person getPerson() {
        return person;
    }

    /**
     * Sets the {@link Person} entity for the librarian.
     *
     * @param person the new {@link Person} entity
     */
    public void setPerson(Person person) {
        this.person = person;
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
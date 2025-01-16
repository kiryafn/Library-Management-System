package com.library.library_management.data.entities;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "LIBRARIAN")
public class Librarian {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "person_id", nullable = false, unique = true)
    private Person person;

    @Column(nullable = false)
    private LocalDate employmentDate;

    @Column(nullable = false)
    private String position;

    public Librarian() {}

    public Librarian(Person person, LocalDate employmentDate, String position) {
        this.person = person;
        this.employmentDate = employmentDate;
        this.position = position;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Person getUser() {
        return person;
    }

    public void setUser(Person person) {
        this.person = person;
    }

    public LocalDate getEmploymentDate() {
        return employmentDate;
    }

    public void setEmploymentDate(LocalDate employmentDate) {
        this.employmentDate = employmentDate;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "Librarian{" +
                "id=" + id +
                ", person=" + person +
                ", employmentDate=" + employmentDate +
                ", position='" + position + '\'' +
                '}';
    }
}
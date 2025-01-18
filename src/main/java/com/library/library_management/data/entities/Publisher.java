package com.library.library_management.data.entities;

import jakarta.persistence.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "PUBLISHER")
public class Publisher {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private Integer id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "PHONENUMBER", length = 20)
    private String phonenumber;

    @OneToMany(mappedBy = "publisher")
    private Set<Book> books;

    public Publisher() {}

    public Publisher(Integer id, String name, String address, String phonenumber) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phonenumber = phonenumber;
    }

    public Publisher(String name, String address, String phonenumber) {
        this.name = name;
        this.address = address;
        this.phonenumber = phonenumber;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }

}
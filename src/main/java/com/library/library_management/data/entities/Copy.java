package com.library.library_management.data.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "COPY")
public class Copy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @Column(nullable = false)
    private Integer copyNumber;

    @Column(nullable = false)
    private String status;

    public Copy() {}

    public Copy(Book book, Integer copyNumber, String status) {
        this.book = book;
        this.copyNumber = copyNumber;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Integer getCopyNumber() {
        return copyNumber;
    }

    public void setCopyNumber(Integer copyNumber) {
        this.copyNumber = copyNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Удобные методы для работы со статусами
    public boolean isAvailable() {
        return this.status == CopyStatus.Available.getName();
    }

    public void markAsAvailable() {
        this.status = CopyStatus.Available.getName();
    }

    public void markAsBorrowed() {
        this.status = CopyStatus.Borrowed.getName();
    }

    public void markAsReserved() {
        this.status = CopyStatus.Reserved.getName();
    }

    public void markAsLost() {
        this.status = CopyStatus.LOST.getName();
    }
}